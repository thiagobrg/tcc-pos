import axios from "axios";

export const api = axios.create({
  baseURL: "http://localhost:8081/",
  timeout: 1000,
  headers: { "X-Custom-Header": "foobar" },
});

api.interceptors.request.use(
  (config) => {
    const token = sessionStorage.getItem("token");
    if (token) {
      config.headers["Authorization"] = token;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);
