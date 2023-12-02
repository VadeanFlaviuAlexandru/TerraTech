import { useEffect } from "react";
import { Link, useLocation } from "react-router-dom";
import { fetchUserTableData } from "../../api/manager/ManagerApi";
import DataTable from "../../components/dataTable/DataTable";
import GoBackIcon from "../../components/icons/GoBackIcon";
import { resetAllManagers } from "../../store/AllManagers/AllManagersSlice";
import {
  resetUsersTable,
  usersTableSetter,
} from "../../store/UsersTable/UsersTableSlice";
import { useAppDispatch, useAppSelector } from "../../store/hooks";
import { columnsUser } from "../../utils/data/columns";
import { ForceSignOut } from "../../utils/userUtils/userUtils";
import "./managerUsers.scss";

export default function ManagerUsers() {
  const dispatch = useAppDispatch();
  const location = useLocation();
  const id = location?.state?.id;
  const currentUser = useAppSelector((state) => state.currentUser);
  const usersTable = useAppSelector((state) => state.usersTable);

  useEffect(() => {
    if (currentUser.user.role === "ROLE_EMPLOYEE") {
      ForceSignOut();
    }
    resetUsersTable();
    resetAllManagers();
    if (currentUser.user.role === "ROLE_ADMIN") {
      fetchUserTableData(id).then((response) => {
        dispatch(usersTableSetter(response));
      });
    }
    return () => {
      resetUsersTable();
    };
  }, []);

  return (
    <div className="users">
      <div className="info">
        <Link className="backButton" to={`/dashboard/users`}>
          <GoBackIcon />
        </Link>
      </div>
      <DataTable
        userTable={true}
        slug="users"
        columns={columnsUser}
        rows={usersTable.users}
      />
    </div>
  );
}
