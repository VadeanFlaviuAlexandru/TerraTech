import ProfileIcon from "../../components/icons/ProfileIcon";
import UsersIcon from "../../components/icons/UsersIcon";
import ProductsIcon from "../../components/icons/ProductsIcon";
import StatisticsIcon from "../../components/icons/StatisticsIcon";
import { GridColDef } from "@mui/x-data-grid";
import { useAppSelector } from "../../store/hooks";
import { Link } from "react-router-dom";
import DocumentIcon from "../../components/icons/DocumentIcon";
import "../../components/menu/menu.scss";

export const MenuList = () => {
  const user = useAppSelector((state) => state.currentUser.user);

  const menu = [];

  switch (user.role) {
    case "ROLE_MANAGER":
      menu.push(
        {
          id: 1,
          title: "Home",
          listItems: [
            {
              id: 2,
              title: "Activity",
              url: "/dashboard/activity",
              icon: <DocumentIcon />,
            },
            {
              id: 3,
              title: "Profile",
              url: `/dashboard/users/${user.id}`,
              icon: <ProfileIcon />,
            },
          ],
        },
        {
          id: 4,
          title: "Managing",
          listItems: [
            {
              id: 5,
              title: "Statistics",
              url: "/dashboard/home",
              icon: <StatisticsIcon />,
            },
            {
              id: 6,
              title: "Users",
              url: "/dashboard/users",
              icon: <UsersIcon />,
            },
            {
              id: 7,
              title: "Products",
              url: "/dashboard/products",
              icon: <ProductsIcon />,
            },
          ],
        }
      );
      break;
    case "ROLE_ADMIN":
      menu.push(
        {
          id: 1,
          title: "Personal",
          listItems: [
            {
              id: 3,
              title: "Profile",
              url: `/dashboard/users/${user.id}`,
              icon: <ProfileIcon />,
            },
          ],
        },
        {
          id: 4,
          title: "Admin",
          listItems: [
            {
              id: 5,
              title: "Statistics",
              url: "/dashboard/home",
              icon: <StatisticsIcon />,
            },
            {
              id: 6,
              title: "Managers",
              url: "/dashboard/users",
              icon: <UsersIcon />,
            },
          ],
        }
      );
      break;
    default:
      menu.push({
        id: 1,
        title: "Home",
        listItems: [
          {
            id: 2,
            title: "Activity",
            url: "/dashboard/activity",
            icon: <DocumentIcon />,
          },
          {
            id: 3,
            title: "Profile",
            url: `/dashboard/users/${user.id}`,
            icon: <ProfileIcon />,
          },
        ],
      });
  }

  return (
    <div className="menu">
      {menu.map((item) => (
        <div className="item" key={item.id}>
          <span className="title">{item.title}</span>
          {item.listItems.map((listItem) => (
            <Link to={listItem.url} className="listItem" key={listItem.id}>
              <div className="icon">{listItem.icon}</div>
              <span className="listItemTitle">{listItem.title}</span>
            </Link>
          ))}
        </div>
      ))}
    </div>
  );
};

export const columnsUserProfile: GridColDef[] = [
  {
    field: "firstName",
    headerName: "First name",
    width: 150,
    type: "string",
    maxWidth: 20,
  },
  {
    field: "lastName",
    headerName: "Last name",
    width: 150,
    type: "string",
    maxWidth: 20,
  },
  {
    field: "email",
    headerName: "E-mail",
    width: 150,
    type: "string",
    maxWidth: 20,
  },
  {
    field: "phone",
    headerName: "Phone",
    width: 100,
    type: "string",
    maxWidth: 10,
  },
];

export const columnsUser: GridColDef[] = [
  {
    field: "status",
    headerName: "Status",
    width: 100,
    type: "boolean",
  },
  {
    field: "firstName",
    headerName: "First name",
    width: 150,
  },
  {
    field: "lastName",
    headerName: "Last name",
    width: 150,
  },
  {
    field: "email",
    headerName: "E-mail",
    width: 250,
  },
  {
    field: "phone",
    headerName: "Phone",
    width: 150,
  },
  {
    field: "createdAt",
    headerName: "Joined at",
    width: 150,
    editable: true,
  },
];

export const columnsProduct: GridColDef[] = [
  {
    field: "name",
    headerName: "Name",
    width: 200,
  },
  {
    field: "producer",
    headerName: "Producer",
    width: 150,
  },
  {
    field: "price",
    headerName: "Price",
    width: 100,
  },
  {
    field: "addedAt",
    headerName: "Added at",
    width: 110,
  },
  {
    field: "inStock",
    headerName: "In stock",
    width: 110,
  },
  {
    field: "numberOfReports",
    headerName: "Number of reports",
    width: 190,
  },
];

export const columnsProductModal: GridColDef[] = [
  {
    field: "name",
    headerName: "Name",
    width: 200,
    maxWidth: 20,
  },
  {
    field: "producer",
    headerName: "Producer",
    width: 150,
    maxWidth: 20,
  },
  {
    field: "price",
    headerName: "Price",
    width: 100,
    maxWidth: 9,
  },
  {
    field: "inStock",
    headerName: "In stock",
    width: 110,
    maxWidth: 9,
  },
];

export const columnsReportModal: GridColDef[] = [
  {
    field: "peopleNotified",
    headerName: "People notified to",
    width: 150,
    maxWidth: 7,
  },
  {
    field: "peopleSold",
    headerName: "People sold to",
    width: 150,
    maxWidth: 7,
  },
  {
    field: "description",
    headerName: "Description",
    width: 200,
  },
];

export const columnsUserModal: GridColDef[] = [
  {
    field: "firstName",
    headerName: "First name",
    width: 150,
    maxWidth: 20,
  },
  {
    field: "lastName",
    headerName: "Last name",
    width: 150,
    maxWidth: 20,
  },
  {
    field: "email",
    headerName: "E-mail",
    width: 250,
    maxWidth: 20,
  },
  {
    field: "phone",
    headerName: "Phone",
    width: 150,
    maxWidth: 10,
  },
];
