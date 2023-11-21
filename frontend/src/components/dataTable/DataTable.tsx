import { Link } from "react-router-dom";
import "./dataTable.scss";
import { DataGrid, GridColDef, GridToolbar } from "@mui/x-data-grid";
import {
  UsersTableState,
  removeEmployee,
  resetUsersTable,
} from "../../store/UsersTable/UsersTableSlice";
import { deleteEmployee } from "../../api/manager/ManagerApi";
import { useAppDispatch, useAppSelector } from "../../store/hooks";
import { warningToast } from "../../utils/toasts/userToasts";
import { useEffect } from "react";
import {
  ProductsTableState,
  removeProduct,
} from "../../store/ProductsTable/ProductTableSlice";
import { deleteProduct } from "../../api/product/ProductApi";
import {
  AllManagersState,
  resetAllManagers,
} from "../../store/AllManagers/AllManagersSlice";

type Props = {
  columns: GridColDef[];
  userTable: boolean;
  rows:
    | UsersTableState["users"]
    | ProductsTableState["products"]
    | AllManagersState["managers"];
  slug: string;
};

export default function DataTable(props: Props) {
  const dispatch = useAppDispatch();
  const currentUser = useAppSelector((state) => state.currentUser);

  const handleDelete = (id: number) => {
    const fetchData = async () => {
      try {
        if (props.userTable) {
          const response = await deleteEmployee(id, currentUser.token);
          if (response.ok) {
            dispatch(removeEmployee(id));
          }
        } else {
          const response = await deleteProduct(id, currentUser.token);
          if (response.ok) {
            dispatch(removeProduct(id));
          }
        }
      } catch (err: any) {
        warningToast(err.stringify);
      }
    };
    fetchData();
  };

  useEffect(() => {
    return () => {
      dispatch(resetUsersTable());
    };
  }, []);

  const actionColumn: GridColDef = {
    field: "action",
    headerName: "Action",
    width: 200,
    renderCell: (params) => {
      return (
        <div className="action">
          <Link
            state={{ id: params.row.id }}
            to={`/dashboard/${props.slug}/${params.row.id}`}
          >
            <img src="../../../view.svg" alt="" />
          </Link>
          <div className="delete" onClick={() => handleDelete(params.row.id)}>
            <img src="../../../delete.svg" alt="" />
          </div>
          {currentUser.user.role === "ROLE_ADMIN" && (
            <Link
              state={{ id: params.row.id }}
              to={`/dashboard/users/manager/${params.row.id}`}
            >
              <img src="../../../view.svg" alt="" />
            </Link>
          )}
        </div>
      );
    },
  };

  return (
    <div className="dataTable">
      <DataGrid
        className="dataGrid"
        rows={props.rows}
        columns={[...props.columns, actionColumn]}
        initialState={{
          pagination: {
            paginationModel: {
              pageSize: 5,
            },
          },
        }}
        pageSizeOptions={[5]}
        checkboxSelection
        disableRowSelectionOnClick
        slots={{ toolbar: GridToolbar }}
        slotProps={{
          toolbar: {
            showQuickFilter: true,
            quickFilterProps: { debounceMs: 300 },
          },
        }}
        disableColumnFilter
        disableColumnSelector
        disableDensitySelector
      />
    </div>
  );
}
