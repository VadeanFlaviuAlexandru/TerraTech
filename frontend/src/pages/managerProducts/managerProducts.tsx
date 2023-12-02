import { useEffect } from "react";
import { Link, useLocation } from "react-router-dom";
import { fetchProductTableData } from "../../api/product/ProductApi";
import DataTable from "../../components/dataTable/DataTable";
import GoBackIcon from "../../components/icons/GoBackIcon";
import { resetAllManagers } from "../../store/AllManagers/AllManagersSlice";
import {
    productTableSetter,
    resetProductTable,
} from "../../store/ProductsTable/ProductTableSlice";
import { useAppDispatch, useAppSelector } from "../../store/hooks";
import { columnsProduct } from "../../utils/data/columns";
import { ForceSignOut } from "../../utils/userUtils/userUtils";
import "./managerProducts.scss";

export default function ManagerProducts() {
  const dispatch = useAppDispatch();
  const location = useLocation();
  const id = location?.state?.id;
  const currentUser = useAppSelector((state) => state.currentUser);
  const currentProducts = useAppSelector((state) => state.productsTable);

  useEffect(() => {
    if (currentUser.user.role === "ROLE_EMPLOYEE") {
      ForceSignOut();
    }
    resetProductTable();
    resetAllManagers();
    if (currentUser.user.role === "ROLE_ADMIN") {
      fetchProductTableData(id).then((response) => {
        dispatch(productTableSetter(response));
      });
    }
    return () => {
      resetProductTable();
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
        userTable={false}
        slug="products"
        columns={columnsProduct}
        rows={currentProducts.products}
      />
    </div>
  );
}
