import { createSlice } from "@reduxjs/toolkit";

export interface AllManagersState {
  managers: {
    id: number;
    firstName: string;
    lastName: string;
    email: string;
    phone: string;
    addedAt: string;
    status: boolean;
  }[];
}

const initialState: AllManagersState = {
  managers: [],
};

export const allManagersSlice = createSlice({
  name: "allManagers",
  initialState,
  reducers: {
    allManagersSetter: (state, action) => {
      state.managers = [...action.payload];
    },
    resetAllManagers: () => {
      return initialState;
    },
  },
});

export const { allManagersSetter, resetAllManagers } = allManagersSlice.actions;
export default allManagersSlice.reducer;
