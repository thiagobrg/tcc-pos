"use client";

import { api } from "@/utils/axiosConfig";
import { Box, Button, Container, TextField, Typography, Link } from "@mui/material";
import { useRouter } from "next/navigation";
import React, { useState } from "react";
import Image from "next/image";
import { User, useAuth } from "@/context/AuthProvider";
import { jwtDecode } from "jwt-decode";

const LoginPage: React.FC = () => {
  const router = useRouter();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const { login } = useAuth();

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();
    try {
      const response = await api.post("/login", { email, password });
      if (response.status === 200) {
        const token: string = response.headers["authorization"];
        const decodedToken: any = jwtDecode(token.replace("Bearer ", ""));
        const user: User = JSON.parse(decodedToken.user);

        if (token && user) {
          login(token, user);
        } else {
          setError("Login failed. No token received.");
        }
      } else {
        setError("Login failed. Please try again.");
      }
    } catch (error) {
      setError("Login failed. Please try again.");
    }
  };
  
  return (
    <Container maxWidth="sm">
      <Box
        sx={{
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          mt: 8,
        }}
      >
        <Typography variant="h2" component="h1" gutterBottom>
          FINANCE
        </Typography>
        <Box component="form" onSubmit={handleSubmit} sx={{ mt: 1 }}>
          <TextField
            margin="normal"
            required
            fullWidth
            id="email"
            label="Email Address"
            name="email"
            autoComplete="off"
            autoFocus
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
          <TextField
            margin="normal"
            required
            fullWidth
            name="password"
            label="Password"
            type="password"
            id="password"
            autoComplete="off"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
          {error && (
            <Typography color="error" variant="body2">
              {error}
            </Typography>
          )}
          <Button
            type="submit"
            fullWidth
            variant="contained"
            sx={{ mt: 3, mb: 2 }}
          >
            Sign In
          </Button>
        </Box>
        <Link href="/register" sx={{ mt: 2 }}>
          <Button variant="text">Don&apos;t have an account? Register</Button>
        </Link>
      </Box>
    </Container>
  );
};

export default LoginPage;
