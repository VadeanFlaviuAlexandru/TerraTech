import { Provider } from "react-redux";
import { Outlet, RouterProvider, createBrowserRouter } from "react-router-dom";
import Footer from "./components/footer/Footer";
import Menu from "./components/menu/Menu";
import Navbar from "./components/navbar/Navbar";
import Home from "./pages/home/Home";
import Login from "./pages/login/Login";
import Product from "./pages/product/Product";
import Products from "./pages/products/Products";
import User from "./pages/user/User";
import Users from "./pages/users/Users";
import { persistor, store } from "./store/store";
import "./styles/global.scss";
import SignUp from "./pages/signup/SignUp";
import Main from "./pages/main/Main";
import { PersistGate } from "redux-persist/integration/react";
import { ToastContainer } from "react-toastify";
import Activity from "./pages/activity/Activity";
import ManagerUsers from "./pages/managerUsers/ManagerUsers";

function App() {
  const Layout = () => {
    return (
      <div className="main">
        <Navbar />
        <div className="container">
          <div className="menuContainer">
            <Menu />
          </div>
          <div className="contentContainer">
            <Outlet />
          </div>
        </div>
        <Footer />
      </div>
    );
  };

  const router = createBrowserRouter([
    {
      path: "dashboard",
      element: <Layout />,
      children: [
        {
          path: "activity",
          element: <Activity />,
        },
        {
          path: "home",
          element: <Home />,
        },
        {
          path: "users",
          element: <Users />,
        },
        {
          path: "users/:id",
          element: <User />,
        },
        {
          path: "products",
          element: <Products />,
        },
        {
          path: "products/:id",
          element: <Product />,
        },
        {
          path: "users/manager/:id",
          element: <ManagerUsers />,
        },
      ],
    },
    {
      path: "/",
      element: <Main />,
    },
    {
      path: "/login",
      element: <Login />,
    },
    {
      path: "/signup",
      element: <SignUp />,
    },
  ]);

  return (
    <Provider store={store}>
      <ToastContainer />
      <PersistGate loading={null} persistor={persistor}>
        <RouterProvider router={router} />
      </PersistGate>
    </Provider>
  );
}

export default App;
