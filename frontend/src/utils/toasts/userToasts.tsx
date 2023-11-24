import { toast } from "react-toastify";

export const successToast = (message: string) =>
  toast.success(`${message}`, { autoClose: 2000 });
export const warningToast = (message: string) =>
  toast.warning(`${message}`, { autoClose: 2000 });
export const errorToast = (message: string) =>
  toast.error(`${message}`, { autoClose: 2200 });
export const shortSuccessToast = (message: string) =>
  toast.success(`${message}`, { autoClose: 1000 });
export const shortWarningToast = (message: string) =>
  toast.warning(`${message}`, { autoClose: 1000 });
export const shortErrorToast = (message: string) =>
  toast.error(`${message}`, { autoClose: 1000 });
export const longSuccessToast = (message: string) =>
  toast.success(`${message}`, { autoClose: 4500 });
export const longWarningToast = (message: string) =>
  toast.warning(`${message}`, { autoClose: 4500 });
export const longErrorToast = (message: string) =>
  toast.error(`${message}`, { autoClose: 4500 });
