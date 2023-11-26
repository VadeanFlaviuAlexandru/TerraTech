import { createSlice } from "@reduxjs/toolkit";

export interface SelectedUserState {
  user: {
    id: number;
    firstName: string;
    lastName: string;
    createdAd: string;
    email: string;
    phone: string;
    status: boolean;
  };
  listOfEmployees: [];
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

const initialState: SelectedUserState = {
  user: {
    id: NaN,
    firstName: "",
    lastName: "",
    createdAd: "",
    email: "",
    phone: "",
    status: false,
  },
  listOfEmployees: [],
  chartInfo: {
    dataKeys: [],
    data: [],
  },
  reports: [],
};

export const selectedUserSlice = createSlice({
  name: "selectedUser",
  initialState,
  reducers: {
    selectedUserSetter: (state, action) => {
      return { ...state, ...action.payload };
    },
    resetSelectedUser: () => {
      return initialState;
    },
    updateSelectedUser: (state, action) => {
      state.user = action.payload;
    },
    updateSelectedReports: (state, action) => {
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
    deleteSelectedReport: (state, action) => {
      const updatedReports = state.reports.filter(
        (report) => report.id !== action.payload.id
      );
      state.reports = updatedReports;
    },
  },
});

export const {
  selectedUserSetter,
  updateSelectedReports,
  resetSelectedUser,
  updateSelectedUser,
  deleteSelectedReport,
} = selectedUserSlice.actions;
export default selectedUserSlice.reducer;
