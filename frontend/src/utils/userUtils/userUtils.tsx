import { Link, useNavigate } from "react-router-dom";
import LogOutIcon from "../../components/icons/LogOutIcon";
import { resetAllManagers } from "../../store/AllManagers/AllManagersSlice";
import { resetUserSetter } from "../../store/CurrentUser/CurrentUserSlice";
import { resetProductTable } from "../../store/ProductsTable/ProductTableSlice";
import { resetSelectedProduct } from "../../store/SelectedProduct/SelectedProductSlice";
import { resetSelectedUser } from "../../store/SelectedUser/SelectedUserSlice";
import { resetStatistics } from "../../store/Statistics/StatisticsSlice";
import { resetUsersTable } from "../../store/UsersTable/UsersTableSlice";
import { useAppDispatch } from "../../store/hooks";
import { longWarningToast, successToast } from "../toasts/userToasts";
import Cookies from "js-cookie";

const dispatching = () => {
  const dispatch = useAppDispatch();
  dispatch(resetUserSetter());
  dispatch(resetAllManagers());
  dispatch(resetProductTable());
  dispatch(resetSelectedProduct());
  dispatch(resetSelectedUser());
  dispatch(resetStatistics());
  dispatch(resetUsersTable());
  Cookies.remove("TerraTech_Access_Token");
};

export const SignOut = () => {
  const handleSignOut = () => {
    dispatching();
    successToast("Goodbye!");
  };

  return (
    <Link to="/" onClick={handleSignOut}>
      <LogOutIcon />
    </Link>
  );
};

export const ForceSignOut = () => {
  const navigate = useNavigate();
  dispatching();
  longWarningToast(
    "You're accessing unauthorized content! If there's a mistake, please contant your superior."
  );
  navigate("/");
};

export const token = Cookies.get("TerraTech_Access_Token");
