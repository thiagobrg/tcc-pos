"use client";

import React, { useState, useEffect } from 'react';
import ProtectedRoute from "@/components/ProtectedRoute";
import ResponsiveAppBar from "@/components/appbar";
import { api } from "@/utils/axiosConfig";
import {
  Card,
  CardContent,
  CardHeader,
  CssBaseline,
  Grid,
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow,
  Button,
  Box,
  IconButton,
} from "@mui/material";
import TransactionModal from '@/components/TransactionModal';
import CategoryModal from '@/components/CategoryModal';
import { Edit, Delete } from '@mui/icons-material';
import { toast } from 'react-toastify';
import { useAuth } from '@/context/AuthProvider';

export interface Content {
  id: number | null; 
  description: string; 
  value: number; 
  date: string; 
  type: "IN" | "OUT"; 
  userId: number; 
  userName: string; 
  categoryId: number | null; 
  categoryName: string | null; 
}

export default function Home() {
  const [saldoAtual, setSaldoAtual] = useState(0);
  const [entrada, setEntrada] = useState(0);
  const [saida, setSaida] = useState(0);
  const [tableContent, setTableContent] = useState<Content[]>([]);
  const [openTransactionModal, setOpenTransactionModal] = useState(false);
  const [openCategoryModal, setOpenCategoryModal] = useState(false);
  const [editTransaction, setEditTransaction] = useState<Content | null>(null);
  const { user } = useAuth();

  const handleOpenTransactionModal = () => setOpenTransactionModal(true);
  const handleCloseTransactionModal = () => {
    setOpenTransactionModal(false);
    setEditTransaction(null);
  };

  const handleOpenCategoryModal = () => setOpenCategoryModal(true);
  const handleCloseCategoryModal = () => setOpenCategoryModal(false);

  useEffect(() => {
    handleTransactionAdded();
  }, []);

  const formCurrency = new Intl.NumberFormat("pt-BR", {
    style: "currency",
    currency: "BRL",
    minimumFractionDigits: 2,
  });

  const handleTransactionAdded = async () => {
    const updatedTransactions = await api.get<Content[]>(`/core/transaction/user/${user.id}`);
    setTableContent(updatedTransactions.data);
    setEntrada(
      updatedTransactions.data.reduce((accumulator, currentValue) => {
        if (currentValue.type === "IN") {
          accumulator += currentValue.value;
        }
        return accumulator;
      }, 0)
    );

    setSaida(
      updatedTransactions.data.reduce((accumulator, currentValue) => {
        if (currentValue.type === "OUT") {
          accumulator += currentValue.value;
        }
        return accumulator;
      }, 0)
    );

    setSaldoAtual(updatedTransactions.data.reduce((acc, cur) => {
      return cur.type === "IN" ? acc + cur.value : acc - cur.value;
    }, 0));
  };

  const handleEdit = (transaction: Content) => {
    setEditTransaction(transaction);
    setOpenTransactionModal(true);
  };

  const handleDelete = async (id: number) => {
    try {
      const response = await api.delete(`/core/transaction/${id}`);
      if (response.status === 200) {
        toast.success('Transação deletada com sucesso!');
        handleTransactionAdded();
      } else {
        toast.error('Falha ao deletar transação.');
      }
    } catch (error) {
      toast.error('Falha ao deletar transação.');
    }
  };

  return (
    <ProtectedRoute>
      <CssBaseline />
      <ResponsiveAppBar />
      <Grid container spacing={2} p={2}>
        <Grid item xs={4}>
          <Card>
            <CardHeader title="SALDO ATUAL" />
            <CardContent>{formCurrency.format(saldoAtual)}</CardContent>
          </Card>
        </Grid>
        <Grid item xs={4}>
          <Card>
            <CardHeader title="ENTRADA" />
            <CardContent>{formCurrency.format(entrada)}</CardContent>
          </Card>
        </Grid>
        <Grid item xs={4}>
          <Card>
            <CardHeader title="SAÍDA" />
            <CardContent>{formCurrency.format(saida)}</CardContent>
          </Card>
        </Grid>
        <Grid item xs={12}>
          <Card>
            <CardHeader title="TRANSAÇÕES" action={
              <Box>
                <Button variant="contained" color="primary" onClick={handleOpenTransactionModal}>
                  NOVA TRANSAÇÃO
                </Button>
                <Button variant="contained" color="secondary" onClick={handleOpenCategoryModal} sx={{ ml: 2 }}>
                  NOVA CATEGORIA
                </Button>
              </Box>
            } />
            <CardContent>
              <Table>
                <TableHead>
                  <TableRow>
                    <TableCell>Data</TableCell>
                    <TableCell>Tipo</TableCell>
                    <TableCell>Categoria</TableCell>
                    <TableCell>Descrição</TableCell>
                    <TableCell>Valor</TableCell>
                    <TableCell>Ações</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {tableContent.map((transaction) => (
                    <TableRow key={transaction.id}>
                      <TableCell>{transaction.date}</TableCell>
                      <TableCell>{transaction.type}</TableCell>
                      <TableCell>{transaction.categoryName}</TableCell>
                      <TableCell>{transaction.description}</TableCell>
                      <TableCell>
                        {formCurrency.format(transaction.value)}
                      </TableCell>
                      <TableCell>
                        <IconButton onClick={() => handleEdit(transaction)}>
                          <Edit />
                        </IconButton>
                        <IconButton onClick={() => handleDelete(transaction.id!)}>
                          <Delete />
                        </IconButton>
                      </TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </CardContent>
          </Card>
        </Grid>
      </Grid>
      <TransactionModal
        open={openTransactionModal}
        onClose={handleCloseTransactionModal}
        onTransactionAdded={handleTransactionAdded}
        editTransaction={editTransaction}
      />
      <CategoryModal
        open={openCategoryModal}
        onClose={handleCloseCategoryModal}
      />
    </ProtectedRoute>
  );
}
