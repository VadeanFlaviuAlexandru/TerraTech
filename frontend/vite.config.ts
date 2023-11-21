import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      "/TerraTechInc": {
        target: "http://localhost:8080",
        changeOrigin: true,
      },
    },
  },
});
