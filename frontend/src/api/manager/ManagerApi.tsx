import { errorToast, successToast } from "../../utils/toasts/userToasts";
import { token } from "../../utils/userUtils/userUtils";

export const managerAddUser = async (payload = {}) => {
  let headers: { [key: string]: string } = {
    "Content-type": "application/json",
  };

  if (token !== "") {
    headers.Authorization = `Bearer ${token}`;
  }

  const response = await fetch("/TerraTechInc/manager/addEmployee", {
    method: "POST",
    body: JSON.stringify(payload),
    headers: headers,
  });
  if (response.ok) {
    const data = await response.json();
    successToast("User added successfully!");
    return data;
  } else if (response.status === 403) {
    errorToast("Invalid credentials and/or access level");
    throw new Error("Invalid credentials and/or access level");
  } else {
    errorToast("Failed to fetch user data");
    throw new Error("Failed to fetch user data");
  }
};

export const updateUserData = async (id: number | string, payload = {}) => {
  let headers: { [key: string]: string } = {
    "Content-type": "application/json",
  };

  if (token !== "") {
    headers.Authorization = `Bearer ${token}`;
  }

  const response = await fetch(`/TerraTechInc/manager/editEmployee/${id}`, {
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

export const fetchUserData = async (id: number | string) => {
  let headers: { [key: string]: string } = {};

  if (token !== "") {
    headers.Authorization = `Bearer ${token}`;
  }

  const response = await fetch(`/TerraTechInc/manager/getEmployee/${id}`, {
    method: "GET",
    headers: headers,
  });

  if (response.ok) {
    const data = await response.json();
    return data;
  } else if (response.status === 403) {
    errorToast("Invalid credentials and/or access level");
    throw new Error("Invalid credentials and/or access level");
  } else {
    errorToast("Failed to fetch user data");
    throw new Error("Failed to fetch user data");
  }
};

export const fetchUserTableData = async (id: number | string) => {
  let headers: { [key: string]: string } = {};

  if (token !== "") {
    headers.Authorization = `Bearer ${token}`;
  }

  const response = await fetch(
    `/TerraTechInc/manager/getEmployeesOfManager/${id}`,
    {
      method: "GET",
      headers: headers,
    }
  );

  if (response.ok) {
    const data = await response.json();
    return data;
  } else if (response.status === 403) {
    errorToast("Invalid credentials and/or access level");
    throw new Error("Invalid credentials and/or access level");
  } else {
    errorToast("Failed to fetch user data");
    throw new Error("Failed to fetch user data");
  }
};

export const deleteEmployee = async (id: number | string) => {
  let headers: { [key: string]: string } = {};

  if (token !== "") {
    headers.Authorization = `Bearer ${token}`;
  }

  const response = await fetch(`/TerraTechInc/manager/deleteEmployee/${id}`, {
    method: "DELETE",
    headers: headers,
  });

  if (response.ok) {
    return response;
  } else if (response.status === 403) {
    errorToast("Invalid credentials and/or access level");
    throw new Error("Invalid credentials and/or access level");
  } else {
    errorToast("Failed to fetch user data");
    throw new Error("Failed to fetch user data");
  }
};

export const changeStatus = async (id: number | string) => {
  let headers: { [key: string]: string } = {
    "Content-type": "application/json",
  };

  if (token !== "") {
    headers.Authorization = `Bearer ${token}`;
  }

  const response = await fetch(`/TerraTechInc/manager/changeStatus/${id}`, {
    method: "PUT",
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

export const deleteReport = async (id: number | string) => {
  let headers: { [key: string]: string } = {};

  if (token !== "") {
    headers.Authorization = `Bearer ${token}`;
  }

  const response = await fetch(`/TerraTechInc/report/deleteReport/${id}`, {
    method: "DELETE",
    headers: headers,
  });

  if (response.ok) {
    successToast("Deleted successfully!");
    return response;
  } else if (response.status === 403) {
    errorToast("Invalid credentials and/or access level");
    throw new Error("Invalid credentials and/or access level");
  } else {
    errorToast("Failed to fetch user data");
    throw new Error("Failed to fetch user data");
  }
};
