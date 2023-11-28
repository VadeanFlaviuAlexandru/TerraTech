import { useEffect, useState } from "react";
import { Link, useLocation } from "react-router-dom";
import { fetchUserTableData } from "../../api/manager/ManagerApi";
import DataTable from "../../components/dataTable/DataTable";
import UserModal from "../../components/modals/userModal/UserModal";
import { resetAllManagers } from "../../store/AllManagers/AllManagersSlice";
import {
  resetUsersTable,
  usersTableSetter,
} from "../../store/UsersTable/UsersTableSlice";
import { useAppDispatch, useAppSelector } from "../../store/hooks";
import { columnsUser, columnsUserModal } from "../../utils/data/columns";
import "./managerUsers.scss";
import GoBackIcon from "../../components/icons/GoBackIcon";

export default function ManagerUsers() {
  const dispatch = useAppDispatch();
  const location = useLocation();
  const id = location?.state?.id;
  const currentUser = useAppSelector((state) => state.currentUser);
  const usersTable = useAppSelector((state) => state.usersTable);
  const [open, setOpen] = useState(false);

  useEffect(() => {
    resetUsersTable();
    resetAllManagers();
    if (currentUser.user.role === "ROLE_ADMIN") {
      fetchUserTableData(id, currentUser.token).then((response) => {
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
        <Link className="button" to={`/dashboard/users`}>
          <GoBackIcon />
        </Link>
      </div>
      <DataTable
        userTable={true}
        slug="users"
        columns={columnsUser}
        rows={usersTable.users}
      />
      {open && (
        <UserModal
          editableMode={false}
          user={null}
          id={-1}
          self={false}
          buttonText="Add employee"
          headerText="Add a new Employee"
          columns={columnsUserModal}
          setOpen={setOpen}
        />
      )}
    </div>
  );
}
