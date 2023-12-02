import { errorToast, successToast } from "../../utils/toasts/userToasts";
import { token } from "../../utils/userUtils/userUtils";

export const fetchProductTableData = async (id: number | string) => {
  let headers: { [key: string]: string } = {};

  if (token !== "") {
    headers.Authorization = `Bearer ${token}`;
  }

  const response = await fetch(
    `/TerraTechInc/product/getManagerProducts/${id}`,
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

export const deleteProduct = async (id: number | string) => {
  let headers: { [key: string]: string } = {};

  if (token !== "") {
    headers.Authorization = `Bearer ${token}`;
  }

  const response = await fetch(`/TerraTechInc/product/deleteProduct/${id}`, {
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

export const fetchProductData = async (id: number | string) => {
  let headers: { [key: string]: string } = {};

  if (token !== "") {
    headers.Authorization = `Bearer ${token}`;
  }

  const response = await fetch(`/TerraTechInc/product/searchProduct/${id}`, {
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

export const updateProductData = async (id: number | string, payload = {}) => {
  let headers: { [key: string]: string } = {
    "Content-type": "application/json",
  };

  if (token !== "") {
    headers.Authorization = `Bearer ${token}`;
  }

  const response = await fetch(`/TerraTechInc/product/editProduct/${id}`, {
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

export const addProductInTable = async (payload = {}) => {
  let headers: { [key: string]: string } = {
    "Content-type": "application/json",
  };

  if (token !== "") {
    headers.Authorization = `Bearer ${token}`;
  }

  const response = await fetch("/TerraTechInc/product/addProduct", {
    method: "POST",
    body: JSON.stringify(payload),
    headers: headers,
  });
  if (response.ok) {
    const data = await response.json();
    successToast("Product added successfully!");
    return data;
  } else if (response.status === 403) {
    errorToast("Invalid credentials and/or access level");
    throw new Error("Invalid credentials and/or access level");
  } else {
    errorToast("Failed to fetch user data");
    throw new Error("Failed to fetch user data");
  }
};
