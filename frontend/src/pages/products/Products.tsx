import { useEffect, useState } from "react";
import { fetchProductTableData } from "../../api/product/ProductApi";
import DataTable from "../../components/dataTable/DataTable";
import AddProductIcon from "../../components/icons/AddProductIcon";
import ProductModal from "../../components/modals/productModal/ProductModal";
import {
  productTableSetter,
  resetProductTable,
} from "../../store/ProductsTable/ProductTableSlice";
import { useAppDispatch, useAppSelector } from "../../store/hooks";
import { columnsProduct, columnsProductModal } from "../../utils/data/columns";
import { ForceSignOut } from "../../utils/userUtils/userUtils";
import "./products.scss";

export default function Products() {
  const dispatch = useAppDispatch();
  const currentUser = useAppSelector((state) => state.currentUser);
  const currentProducts = useAppSelector((state) => state.productsTable);
  const [open, setOpen] = useState(false);

  useEffect(() => {
    fetchProductTableData(currentUser.user.id).then((response) => {
      dispatch(productTableSetter(response));
    });
    return () => {
      resetProductTable();
    };
  }, [currentUser.user.id]);

  useEffect(() => {
    if (currentUser.user.role === "ROLE_EMPLOYEE") {
      ForceSignOut();
    }
  }, []);

  return (
    <div className="products">
      <div className="info">
        <h1>Products</h1>
        <button className="addButton" onClick={() => setOpen(true)}>
          <AddProductIcon />
        </button>
      </div>
      <DataTable
        userTable={false}
        slug="products"
        columns={columnsProduct}
        rows={currentProducts.products}
      />
      {open && (
        <ProductModal
          product={null}
          editableMode={false}
          id={-1}
          buttonText="Add"
          headerText="Add a new product"
          columns={columnsProductModal}
          setOpen={setOpen}
        />
      )}
    </div>
  );
}
