export const fetchStatistics = async (id: number, token = "") => {
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
    throw new Error("Invalid credentials and/or access level");
  } else {
    throw new Error("Failed to fetch user data");
  }
};

export const fetchAllManagers = async (token = "") => {
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
    throw new Error("Invalid credentials and/or access level");
  } else {
    throw new Error("Failed to fetch user data");
  }
};
