import { successToast } from "../../utils/toasts/userToasts";

export const employeeAddReport = async (payload = {}, token = "") => {
  let headers: { [key: string]: string } = {
    "Content-type": "application/json",
  };

  if (token !== "") {
    headers.Authorization = `Bearer ${token}`;
  }

  const response = await fetch("/TerraTechInc/report/addReport", {
    method: "POST",
    body: JSON.stringify(payload),
    headers: headers,
  });
  if (response.ok) {
    const data = await response.json();
    successToast("Report added successfully!");
    return data;
  } else if (response.status === 403) {
    throw new Error("Invalid credentials and/or access level");
  } else {
    throw new Error("Failed to fetch user data");
  }
};
