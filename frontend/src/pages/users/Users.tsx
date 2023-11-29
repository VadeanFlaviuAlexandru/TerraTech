import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { fetchUserTableData } from "../../api/manager/ManagerApi";
import { fetchAllManagers } from "../../api/statistics/StatisticsApi";
import DataTable from "../../components/dataTable/DataTable";
import AddUserIcon from "../../components/icons/AddUserIcon";
import UserModal from "../../components/modals/userModal/UserModal";
import {
  allManagersSetter,
  resetAllManagers,
} from "../../store/AllManagers/AllManagersSlice";
import {
  resetUsersTable,
  usersTableSetter,
} from "../../store/UsersTable/UsersTableSlice";
import { useAppDispatch, useAppSelector } from "../../store/hooks";
import { columnsUser, columnsUserModal } from "../../utils/data/columns";
import "./users.scss";
import { ForceSignOut } from "../../utils/userUtils/userUtils";

export default function Users() {
  const dispatch = useAppDispatch();
  const currentUser = useAppSelector((state) => state.currentUser);
  const usersTable = useAppSelector((state) => state.usersTable);
  const allManagers = useAppSelector((state) => state.allManagers);
  const id = useParams()?.id;
  const [open, setOpen] = useState(false);

  useEffect(() => {
    if (currentUser.user.role === "ROLE_ADMIN") {
      if (id) {
        fetchUserTableData(id, currentUser?.token).then((response) => {
          dispatch(usersTableSetter(response));
        });
      } else {
        fetchAllManagers(currentUser?.token).then((response) => {
          dispatch(allManagersSetter(response));
        });
      }
    } else {
      fetchUserTableData(currentUser?.user?.id, currentUser?.token).then(
        (response) => {
          dispatch(usersTableSetter(response));
        }
      );
    }
    return () => {
      resetUsersTable();
      resetAllManagers();
    };
  }, [currentUser.user.id]);

  useEffect(() => {
    if (currentUser.token === "ROLE_EMPLOYEE") {
      ForceSignOut();
    }
  }, []);

  return (
    <div className="users">
      <div className="infoUsers">
        <h1>Users</h1>
        <button className="addButton" onClick={() => setOpen(true)}>
          <AddUserIcon />
        </button>
      </div>
      <DataTable
        userTable={true}
        slug="users"
        columns={columnsUser}
        rows={
          currentUser?.user?.role === "ROLE_ADMIN"
            ? allManagers?.managers
            : usersTable?.users
        }
      />
      {open && (
        <UserModal
          buttonText="Add employee"
          headerText="Add a new Employee"
          editableMode={false}
          user={null}
          id={-1}
          self={false}
          columns={columnsUserModal}
          setOpen={setOpen}
        />
      )}
    </div>
  );
}
