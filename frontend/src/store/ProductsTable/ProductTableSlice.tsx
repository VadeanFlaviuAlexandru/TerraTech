import { createSlice } from "@reduxjs/toolkit";

export interface ProductsTableState {
  products: {
    id: number;
    name: string;
    price: number;
    producer: string;
    inStock: number;
    addedAt: string;
    numberOfReports: number;
  }[];
}

const initialState: ProductsTableState = { products: [] };

export const productTableSlice = createSlice({
  name: "productTable",
  initialState,
  reducers: {
    productTableSetter: (state, action) => {
      state.products = action.payload;
    },
    resetProductTable: () => {
      return initialState;
    },
    removeProduct: (state, action) => {
      const productIdToDelete = action.payload;
      state.products = state.products.filter(
        (product) => product.id !== productIdToDelete
      );
    },
    addProduct: (state, action) => {
      state.products = [...state.products, action.payload];
    },
  },
});

export const {
  productTableSetter,
  resetProductTable,
  removeProduct,
  addProduct,
} = productTableSlice.actions;
export default productTableSlice.reducer;
