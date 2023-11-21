import { combineReducers, configureStore } from "@reduxjs/toolkit";
import {
  FLUSH,
  PAUSE,
  PERSIST,
  PURGE,
  REGISTER,
  REHYDRATE,
  persistReducer,
  persistStore,
} from "redux-persist";
import storage from "redux-persist/lib/storage"; // Use the storage of your choice
import currentUserReducer from "./CurrentUser/CurrentUserSlice";
import selectedUserReducer from "./SelectedUser/SelectedUserSlice";
import usersTableReducer from "./UsersTable/UsersTableSlice";
import productsTableReducer from "./ProductsTable/ProductTableSlice";
import selectedProductReducer from "./SelectedProduct/SelectedProductSlice";
import statisticsReducer from "./Statistics/StatisticsSlice";
import allManagersReducer from "./AllManagers/AllManagersSlice";

const rootReducer = combineReducers({
  usersTable: usersTableReducer,
  currentUser: currentUserReducer,
  selectedUser: selectedUserReducer,
  productsTable: productsTableReducer,
  selectedProduct: selectedProductReducer,
  statistics: statisticsReducer,
  allManagers: allManagersReducer,
});

const persistConfig = {
  key: "root",
  storage,
};

const persistedReducer = persistReducer(persistConfig, rootReducer);

export const store = configureStore({
  reducer: persistedReducer,
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({
      serializableCheck: {
        ignoredActions: [FLUSH, REHYDRATE, PAUSE, PERSIST, PURGE, REGISTER],
      },
    }),
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
export const persistor = persistStore(store);
