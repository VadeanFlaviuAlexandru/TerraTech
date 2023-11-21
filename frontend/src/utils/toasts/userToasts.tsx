import { toast } from "react-toastify";

export const successToast = (message: string) =>
  toast.success(`${message}`, { autoClose: 1500 });
export const warningToast = (message: string) =>
  toast.warning(`${message}`, { autoClose: 1500 });
