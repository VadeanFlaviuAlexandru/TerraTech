import { createSlice } from "@reduxjs/toolkit";

export interface UsersTableState {
  users: {
    id: number;
    firstName: string;
    lastName: string;
    email: string;
    phone: string;
    role: string;
    createdAt: string;
    password: string;
    status: boolean;
  }[];
}

const initialState: UsersTableState = { users: [] };

export const usersTableSlice = createSlice({
  name: "usersTable",
  initialState,
  reducers: {
    usersTableSetter: (state, action) => {
      state.users = action.payload;
    },
    resetUsersTable: () => {
      return initialState;
    },
    removeEmployee: (state, action) => {
      const userIdToDelete = action.payload;
      state.users = state.users.filter((user) => user.id !== userIdToDelete);
    },
    addEmployee: (state, action) => {
      state.users = [...state.users, action.payload];
    },
  },
});

export const {
  usersTableSetter,
  resetUsersTable,
  removeEmployee,
  addEmployee,
} = usersTableSlice.actions;
export default usersTableSlice.reducer;
