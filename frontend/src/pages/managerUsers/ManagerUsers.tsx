import DataTable from "../../components/dataTable/DataTable";
import "./managerUsers.scss";
import { useEffect, useState } from "react";
import UserModal from "../../components/modals/userModal/UserModal";
import { fetchUserTableData } from "../../api/manager/ManagerApi";
import { useAppDispatch, useAppSelector } from "../../store/hooks";
import {
  resetUsersTable,
  usersTableSetter,
} from "../../store/UsersTable/UsersTableSlice";
import { warningToast } from "../../utils/toasts/userToasts";
import { columnsUser, columnsUserModal } from "../../utils/data/columns";
import { fetchAllManagers } from "../../api/statistics/StatisticsApi";
import {
  allManagersSetter,
  resetAllManagers,
} from "../../store/AllManagers/AllManagersSlice";
import { Link, useLocation, useParams } from "react-router-dom";

export default function ManagerUsers() {
  const currentUser = useAppSelector((state) => state.currentUser);
  const usersTable = useAppSelector((state) => state.usersTable);
  const allManagers = useAppSelector((state) => state.allManagers);
  const dispatch = useAppDispatch();
  const [open, setOpen] = useState(false);
  const location = useLocation();
  const id = location?.state?.id;
  // const id = useParams()?.id;

  useEffect(() => {
    resetUsersTable();
    resetAllManagers();
    const fetchData = async () => {
      try {
        if (currentUser.user.role === "ROLE_ADMIN") {
          await fetchUserTableData(id, currentUser.token).then((response) => {
            dispatch(usersTableSetter(response));
          });
        }
      } catch (err: any) {
        warningToast(err.stringify);
      }
    };
    fetchData();
    return () => {
      resetUsersTable();
    };
  }, []);

  return (
    <div className="users">
      <Link className="button" to={`/dashboard/users`}>
        ~~Back~~
      </Link>

      <div className="info">
        <h1>Users</h1>
        <button onClick={() => setOpen(true)}>Add new user</button>
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
          buttonText="add new user"
          headerText="user"
          columns={columnsUserModal}
          setOpen={setOpen}
        />
      )}
    </div>
  );
}
