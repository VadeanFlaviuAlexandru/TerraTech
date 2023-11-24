import { errorToast, successToast } from "../../utils/toasts/userToasts";

export const updateReport = async (
  id: number | string,
  payload = {},
  token = ""
) => {
  let headers: { [key: string]: string } = {
    "Content-type": "application/json",
  };

  if (token !== "") {
    headers.Authorization = `Bearer ${token}`;
  }

  const response = await fetch(`/TerraTechInc/report/editReport/${id}`, {
    method: "PUT",
    body: JSON.stringify(payload),
    headers: headers,
  });

  if (response.ok) {
    const data = await response.json();
    successToast("Updated successfully!");
    return data;
  } else if (response.status === 403) {
    errorToast("Invalid credentials and/or access level");
    throw new Error("Invalid credentials and/or access level");
  } else {
    errorToast("Failed to fetch user data");
    throw new Error("Failed to fetch user data");
  }
};
