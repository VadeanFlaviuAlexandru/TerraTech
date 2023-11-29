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

export const SignOut = () => {
  const dispatch = useAppDispatch();

  const handleSignOut = () => {
    dispatch(resetUserSetter());
    dispatch(resetAllManagers());
    dispatch(resetProductTable());
    dispatch(resetSelectedProduct());
    dispatch(resetSelectedUser());
    dispatch(resetStatistics());
    dispatch(resetUsersTable());
    successToast("Goodbye!");
  };

  return (
    <Link to="/" onClick={handleSignOut}>
      <LogOutIcon />
    </Link>
  );
};

export const ForceSignOut = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  dispatch(resetUserSetter());
  dispatch(resetAllManagers());
  dispatch(resetProductTable());
  dispatch(resetSelectedProduct());
  dispatch(resetSelectedUser());
  dispatch(resetStatistics());
  dispatch(resetUsersTable());
  longWarningToast(
    "You're accessing unauthorized content! If there's a mistake, please contant your superior."
  );
  navigate("/");
};
