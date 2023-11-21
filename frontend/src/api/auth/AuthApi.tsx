import { successToast } from "../../utils/toasts/userToasts";

export const logInUser = async (payload = {}) => {
  const headers: { [key: string]: string } = {
    "Content-type": "application/json",
  };

  const response = await fetch(`/TerraTechInc/signIn`, {
    method: "POST",
    body: JSON.stringify(payload),
    headers: headers,
  });

  if (response.ok) {
    const data = await response.json();
    successToast("Welcome!");
    return data;
  } else if (response.status === 403) {
    throw new Error("Invalid credentials and/or access level");
  } else {
    throw new Error("Failed to fetch user data");
  }
};

export const signUpUser = async (payload = {}) => {
  const headers: { [key: string]: string } = {
    "Content-type": "application/json",
  };

  const response = await fetch(`/TerraTechInc/signUp`, {
    method: "POST",
    body: JSON.stringify(payload),
    headers: headers,
  });

  if (response.ok) {
    const data = await response.json();
    successToast("Goodbye!");
    return data;
  } else if (response.status === 403) {
    throw new Error("Invalid credentials and/or access level");
  } else {
    throw new Error("Failed to sign up user");
  }
};
