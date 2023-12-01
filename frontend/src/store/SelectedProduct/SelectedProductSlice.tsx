import { createSlice } from "@reduxjs/toolkit";

interface SelectedProductState {
  product: {
    id: number;
    inStock: number;
    producer: string;
    name: string;
    price: number;
  };
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
}

const initialState: SelectedProductState = {
  product: {
    id: NaN,
    name: "",
    price: NaN,
    producer: "",
    inStock: NaN,
  },
  chartInfo: {
    dataKeys: [],
    data: [],
  },
};

export const selectedProductSlice = createSlice({
  name: "selectedProduct",
  initialState,
  reducers: {
    selectedProductSetter: (state, action) => {
      state.product = action.payload.product;
      state.chartInfo = action.payload.chartInfo;
    },
    resetSelectedProduct: () => {
      return initialState;
    },
    updateSelectedProduct: (state, action) => {
      state.product = action.payload;
    },
  },
});

export const {
  selectedProductSetter,
  resetSelectedProduct,
  updateSelectedProduct,
} = selectedProductSlice.actions;
export default selectedProductSlice.reducer;
