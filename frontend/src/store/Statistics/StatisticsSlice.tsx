import { createSlice } from "@reduxjs/toolkit";

export interface StatisticsState {
  topDealUsers: {
    firstName: string;
    email: string;
    amount: number;
  }[];
  totalUsers: {
    number: number;
    percentage: number;
    chartData: {
      name: string;
      value: number;
    }[];
    dataKey: string;
    title: string;
  };
  totalProducts: {
    number: number;
    percentage: number;
    chartData: {
      name: string;
      value: number;
    }[];
    dataKey: string;
    title: string;
  };
  top5ProductsThisYear: {
    name: string;
    value: number;
  }[];
  totalRevenueThisYear: {
    number: number;
    percentage: number;
    chartData: {
      name: string;
      value: number;
    }[];
    dataKey: string;
    title: string;
  };
  totalNotifiedPeople: {
    number: number;
    percentage: number;
    chartData: {
      name: string;
      value: number;
    }[];
    dataKey: string;
    title: string;
  };
}

const initialState: StatisticsState = {
  topDealUsers: [],
  totalUsers: {
    number: -1,
    percentage: -1,
    chartData: [],
    dataKey: "",
    title: "",
  },
  totalProducts: {
    number: -1,
    percentage: -1,
    chartData: [],
    dataKey: "",
    title: "",
  },
  top5ProductsThisYear: [],
  totalRevenueThisYear: {
    number: -1,
    percentage: -1,
    chartData: [],
    dataKey: "",
    title: "",
  },
  totalNotifiedPeople: {
    number: -1,
    percentage: -1,
    chartData: [],
    dataKey: "",
    title: "",
  },
};

export const statisticsSlice = createSlice({
  name: "statisticsTable",
  initialState,
  reducers: {
    statisticsSetter: (state, action) => {
      return { ...state, ...action.payload };
    },
    resetStatistics: () => {
      return initialState;
    },
  },
});

export const { statisticsSetter, resetStatistics } = statisticsSlice.actions;
export default statisticsSlice.reducer;
