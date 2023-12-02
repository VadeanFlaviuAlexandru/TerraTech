import { createSlice } from "@reduxjs/toolkit";

interface CurrentUserState {
  user: {
    id: number;
    firstName: string;
    lastName: string;
    email: string;
    phone: string;
    role: string;
    createdAt: string;
    password: string;
    status: boolean;
  };
  managerId: number;
  chartInfo: {
    dataKeys: {
      name: string;
    }[];
    data: {
      name: string;
      peopleNotified: number;
      peopleSold: number;
    }[];
  };
  reports: {
    id: number;
    productId: string;
    description: string;
    productName: string;
    createDate: string;
    peopleSold: number;
    peopleNotified: number;
  }[];
}

const initialState: CurrentUserState = {
  user: {
    id: 0,
    firstName: "",
    lastName: "",
    email: "",
    phone: "",
    role: "",
    createdAt: "",
    password: "",
    status: false,
  },
  managerId: NaN,
  chartInfo: {
    dataKeys: [],
    data: [],
  },
  reports: [],
};

export const currentUserSlice = createSlice({
  name: "currentUser",
  initialState,
  reducers: {
    currentUserSetter: (state, action) => {
      return { ...state, ...action.payload };
    },
    updateCurrentUser: (state, action) => {
      state.user = action.payload;
    },
    resetUserSetter: () => {
      return initialState;
    },
    updateCurrentReports: (state, action) => {
      const payload = { ...action.payload };
      const updatedReports = state.reports.map((report) => {
        if (report.id == action.payload.id) {
          return {
            ...report,
            ...payload,
          };
        }
        return report;
      });
      state.reports = updatedReports;
    },
    deleteCurrentReport: (state, action) => {
      const updatedReports = state.reports.filter(
        (report) => report.id != action.payload
      );
      state.reports = updatedReports;
    },
    addReport: (state, action) => {
      const reports = [...state.reports];
      reports.push(action.payload);
      state.reports = reports;
    },
  },
});

export const {
  currentUserSetter,
  resetUserSetter,
  updateCurrentReports,
  deleteCurrentReport,
  addReport,
  updateCurrentUser,
} = currentUserSlice.actions;
export default currentUserSlice.reducer;
