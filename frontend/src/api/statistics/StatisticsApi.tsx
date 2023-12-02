import { errorToast } from "../../utils/toasts/userToasts";
import { token } from "../../utils/userUtils/userUtils";

export const fetchStatistics = async (id: number) => {
  let headers: { [key: string]: string } = {};

  if (token !== "") {
    headers.Authorization = `Bearer ${token}`;
  }

  const response = await fetch(`/TerraTechInc/chart/statistics/${id}`, {
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

export const fetchAllManagers = async () => {
  let headers: { [key: string]: string } = {};

  if (token !== "") {
    headers.Authorization = `Bearer ${token}`;
  }

  const response = await fetch(`/TerraTechInc/admin/getOnlyManagers`, {
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
