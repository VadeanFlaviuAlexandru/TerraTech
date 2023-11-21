import { successToast } from "../../utils/toasts/userToasts";

export const fetchProductTableData = async (
  id: number | string,
  token = ""
) => {
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
    throw new Error("Invalid credentials and/or access level");
  } else {
    throw new Error("Failed to fetch user data");
  }
};

export const deleteProduct = async (id: number | string, token = "") => {
  let headers: { [key: string]: string } = {};

  if (token !== "") {
    headers.Authorization = `Bearer ${token}`;
  }

  const response = await fetch(`/TerraTechInc/product/deleteProduct/${id}`, {
    method: "DELETE",
    headers: headers,
  });

  if (response.ok) {
    return response;
  } else if (response.status === 403) {
    throw new Error("Invalid credentials and/or access level");
  } else {
    throw new Error("Failed to fetch user data");
  }
};

export const fetchProductData = async (id: number | string, token = "") => {
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
    throw new Error("Invalid credentials and/or access level");
  } else {
    throw new Error("Failed to fetch user data");
  }
};

export const updateProductData = async (
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
    throw new Error("Invalid credentials and/or access level");
  } else {
    throw new Error("Failed to fetch user data");
  }
};


export const addProductInTable = async (payload = {}, token = "") => {
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
    successToast("User added successfully!");
    return data;
  } else if (response.status === 403) {
    throw new Error("Invalid credentials and/or access level");
  } else {
    throw new Error("Failed to fetch user data");
  }
};