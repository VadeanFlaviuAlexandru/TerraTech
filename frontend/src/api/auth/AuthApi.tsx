import {
  errorToast,
  longErrorToast,
  shortSuccessToast,
} from "../../utils/toasts/userToasts";

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
    shortSuccessToast("Welcome!");
    return data;
  } else if (response.status === 403) {
    errorToast("Invalid credentials and/or access level");
    throw new Error("Invalid credentials and/or access level");
  } else {
    errorToast("Failed to fetch user data");
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
    shortSuccessToast("Success!");
    return response;
  } else if (response.status === 403) {
    errorToast("Invalid credentials and/or access level");
    throw new Error("Invalid credentials and/or access level");
  } else {
    longErrorToast("An error occured. Please try again!");
    throw new Error("An error occured. Please try again!");
  }
};
