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
  token: string;
  chartInfo: {
    dataKeys: {
      name: string;
    }[];
    data: {
      name: string;
      peopleNotifiedAboutProduct: number;
      peopleSoldTo: number;
    }[];
  };
  reports: {
    id: number;
    productId: string;
    description: string;
    productName: string;
    createDate: string;
    peopleSoldTo: number;
    peopleNotifiedAboutProduct: number;
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
  token: "",
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
    resetUserSetter: () => {
      return initialState;
    },
    updateCurrentReports: (state, action) => {
      const updatedReports = state.reports.map((report) => {
        if (report.id == action.payload.id) {
          return {
            ...report,
            description: action.payload.description,
            productId: action.payload.productId,
            peopleNotifiedAboutProduct:
              action.payload.peopleNotifiedAboutProduct,
            peopleSoldTo: action.payload.peopleSoldTo,
          };
        }
        return report;
      });
      state.reports = updatedReports;
    },
    deleteCurrentReport: (state, action) => {
      const updatedReports = state.reports.filter(
        (report) => report.id !== action.payload.id
      );
      state.reports = updatedReports;
    },
  },
});

export const {
  currentUserSetter,
  resetUserSetter,
  updateCurrentReports,
  deleteCurrentReport,
} = currentUserSlice.actions;
export default currentUserSlice.reducer;
