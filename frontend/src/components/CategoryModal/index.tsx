"use client";

import React, { useState } from "react";
import { Box, Button, Modal, TextField, Typography } from "@mui/material";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { api } from "@/utils/axiosConfig";
import { useAuth } from "@/context/AuthProvider";

interface CategoryModalProps {
  open: boolean;
  onClose: () => void;
}

const CategoryModal: React.FC<CategoryModalProps> = ({ open, onClose }) => {
  const [name, setName] = useState("");
  const { user } = useAuth();

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();
    try {
      const userId = user.id; // Altere para pegar o ID do usuário logado
      const response = await api.post("/core/transaction-category", {
        name,
        userId,
      });

      if (response.status === 201) {
        toast.success("Categoria cadastrada com sucesso!");
        onClose();
      } else {
        toast.error("Falha ao cadastrar categoria.");
      }
    } catch (error) {
      toast.error("Falha ao cadastrar categoria.");
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
          Nova Categoria
        </Typography>
        <TextField
          margin="normal"
          required
          fullWidth
          id="name"
          label="Nome da Categoria"
          name="name"
          autoComplete="name"
          autoFocus
          value={name}
          onChange={(e) => setName(e.target.value)}
        />
        <Button
          type="submit"
          fullWidth
          variant="contained"
          sx={{ mt: 3, mb: 2 }}
        >
          Cadastrar
        </Button>
      </Box>
    </Modal>
  );
};

export default CategoryModal;
