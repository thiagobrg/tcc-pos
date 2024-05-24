"use client";

import React, { useState, useEffect } from "react";
import {
  Box,
  Button,
  Modal,
  TextField,
  MenuItem,
  Select,
  InputLabel,
  FormControl,
  Typography,
} from "@mui/material";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { api } from "@/utils/axiosConfig";
import { Content } from "@/app/home/page";
import { useAuth } from "@/context/AuthProvider";

interface Category {
  id: number;
  name: string;
  userId: number;
  userName: string;
}

interface TransactionModalProps {
  open: boolean;
  onClose: () => void;
  onTransactionAdded: () => void;
  editTransaction?: Content | null;
}

const TransactionModal: React.FC<TransactionModalProps> = ({
  open,
  onClose,
  onTransactionAdded,
  editTransaction,
}) => {
  const [description, setDescription] = useState("");
  const [value, setValue] = useState<number | string>("");
  const [date, setDate] = useState("");
  const [type, setType] = useState("");
  const [categoryId, setCategoryId] = useState<number | string>("");
  const [categories, setCategories] = useState<Category[]>([]);
  const { user } = useAuth();

  useEffect(() => {
    setDescription(editTransaction ? editTransaction.description : "");
    setValue(editTransaction ? editTransaction.value : "");
    setDate(editTransaction ? editTransaction.date : "");
    setType(editTransaction ? editTransaction.type : "");
    setCategoryId(editTransaction ? editTransaction.categoryId || "" : "");

    const fetchCategories = async () => {
      try {
        const response = await api.get<Category[]>(
          "/core/transaction-category"
        );
        setCategories(response.data);
      } catch (error) {
        console.error("Error fetching categories", error);
      }
    };

    fetchCategories();
  }, [open, editTransaction]);

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();
    try {
      const userId = user.id;
      const data = {
        description,
        value,
        date,
        type,
        userId,
        categoryId,
      };

      let response;
      if (editTransaction) {
        response = await api.put(
          `/core/transaction/${editTransaction.id}`,
          data
        );
      } else {
        response = await api.post("/core/transaction", data);
      }

      if (response.status == 200 || response.status == 201) {
        toast.success(
          `Transação ${
            editTransaction ? "atualizada" : "cadastrada"
          } com sucesso!`
        );
        onClose();
        onTransactionAdded();
      } else {
        toast.error(
          `Falha ao ${editTransaction ? "atualizar" : "cadastrar"} transação.`
        );
      }
    } catch (error) {
      toast.error(
        `Falha ao ${editTransaction ? "atualizar" : "cadastrar"} transação.`
      );
    }
  };

  return (
    <Modal
      open={open}
      onClose={onClose}
      aria-labelledby="modal-modal-title"
      aria-describedby="modal-modal-description"
    >
      <Box
        component="form"
        onSubmit={handleSubmit}
        sx={{
          position: "absolute",
          top: "50%",
          left: "50%",
          transform: "translate(-50%, -50%)",
          width: 400,
          bgcolor: "background.paper",
          border: "2px solid #000",
          boxShadow: 24,
          p: 4,
        }}
      >
        <Typography id="modal-modal-title" variant="h6" component="h2">
          {editTransaction ? "Editar Transação" : "Nova Transação"}
        </Typography>
        <TextField
          margin="normal"
          required
          fullWidth
          id="description"
          label="Descrição"
          name="description"
          autoComplete="description"
          autoFocus
          value={description}
          onChange={(e) => setDescription(e.target.value)}
        />
        <TextField
          margin="normal"
          required
          fullWidth
          id="value"
          label="Valor"
          name="value"
          autoComplete="value"
          type="number"
          value={value}
          onChange={(e) => setValue(e.target.value)}
        />
        <TextField
          margin="normal"
          required
          fullWidth
          id="date"
          label="Data"
          name="date"
          type="date"
          InputLabelProps={{
            shrink: true,
          }}
          value={date}
          onChange={(e) => setDate(e.target.value)}
        />
        <FormControl fullWidth margin="normal">
          <InputLabel id="type-label">Tipo</InputLabel>
          <Select
            labelId="type-label"
            id="type"
            value={type}
            label="Tipo"
            onChange={(e) => setType(e.target.value)}
          >
            <MenuItem value={"IN"}>Entrada</MenuItem>
            <MenuItem value={"OUT"}>Saída</MenuItem>
          </Select>
        </FormControl>
        <FormControl fullWidth margin="normal">
          <InputLabel id="category-label">Categoria</InputLabel>
          <Select
            labelId="category-label"
            id="categoryId"
            value={categoryId}
            label="Categoria"
            onChange={(e) => setCategoryId(e.target.value)}
          >
            {categories.map((category) => (
              <MenuItem key={category.id} value={category.id}>
                {category.name}
              </MenuItem>
            ))}
          </Select>
        </FormControl>
        <Button
          type="submit"
          fullWidth
          variant="contained"
          sx={{ mt: 3, mb: 2 }}
        >
          {editTransaction ? "Atualizar" : "Cadastrar"}
        </Button>
      </Box>
    </Modal>
  );
};

export default TransactionModal;
