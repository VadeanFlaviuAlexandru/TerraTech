import {
  longErrorToast,
  shortSuccessToast,
} from "../../utils/toasts/userToasts";
import Cookies from "js-cookie";

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
    console.log(data.token);
    Cookies.set("TerraTech_Access_Token", data.token);
    return data;
  } else if (response.status === 403) {
    longErrorToast("Failed to log in. Please re-enter your credentials.");
    throw new Error("Failed to log in. Please re-enter your credentials.");
  } else {
    longErrorToast("An error occured. Please try again!");
    throw new Error("An error occured. Please try again!");
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
    longErrorToast(
      "An account associated with this e-maill address already exists!"
    );
    throw new Error(
      "An account associated with this e-maill address already exists!"
    );
  } else {
    longErrorToast("An error occured. Please try again!");
    throw new Error("An error occured. Please try again!");
  }
};
