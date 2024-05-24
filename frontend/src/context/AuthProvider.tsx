'use client'

import React, { createContext, useContext, useState, useEffect, ReactNode } from 'react';
import { useRouter } from 'next/navigation';

interface AuthContextProps {
  isAuthenticated: boolean;
  login: (token: string, user: User) => void;
  logout: () => void;
  user : User;
}

export interface User {
  id: number;
  name: string;
  email: string;
  createdDate: Date;
}

const AuthContext = createContext<AuthContextProps | undefined>(undefined);

export const AuthProvider = ({ children }: { children: ReactNode }) => {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [user, setUser] = useState({} as User);
  const router = useRouter();

  useEffect(() => {
    const token = sessionStorage.getItem('token');
    if (token) {
      setIsAuthenticated(true);
    }
  }, []);

  const login = (token: string, user: User) => {
    sessionStorage.setItem('token', token);
    setUser(user);
    setIsAuthenticated(true);
    router.push('/home');
  };

  const logout = () => {
    sessionStorage.removeItem('token');
    setIsAuthenticated(false);
    router.push('/login');
  };

  return (
    <AuthContext.Provider value={{ isAuthenticated, login, logout, user }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = (): AuthContextProps => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};
