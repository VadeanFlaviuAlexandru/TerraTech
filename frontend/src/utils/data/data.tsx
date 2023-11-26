import "../../components/menu/menu.scss";
import { SelectedUserState } from "../../store/SelectedUser/SelectedUserSlice";

export const monthNameToNumber: { [key: string]: number } = {
  JANUARY: 0,
  FEBRUARY: 1,
  MARCH: 2,
  APRIL: 3,
  MAY: 4,
  JUNE: 5,
  JULY: 6,
  AUGUST: 7,
  SEPTEMBER: 8,
  OCTOBER: 9,
  NOVEMBER: 10,
  DECEMBER: 11,
};

export const processedData = (data: SelectedUserState["chartInfo"]["data"]) =>
  data.map((dataPoint) => {
    const monthNumber = monthNameToNumber[dataPoint.name];
    const isFutureMonth = new Date().getMonth() < monthNumber;

    return {
      ...dataPoint,
      peopleNotifiedAboutProduct: isFutureMonth
        ? null
        : dataPoint.peopleNotifiedAboutProduct || 0,
      peopleSoldTo: isFutureMonth ? null : dataPoint.peopleSoldTo || 0,
    };
  });

export function getRandomColor() {
  const presetColors = [
    "#FF5733",
    "#33FF57",
    "#5733FF",
    "#FF33A1",
    "#33A1FF",
    "#ff0000",
    "#ffd500",
    "#ff00bf",
  ];
  const randomIndex = Math.floor(Math.random() * presetColors.length);
  return presetColors[randomIndex];
}

export const UserInfoMappings: Record<string, string> = {
  firstName: "First Name",
  lastName: "Last Name",
  email: "E-mail",
  createdAt: "Created at",
  phone: "Phone",
  role: "Role",
  status: "Status",
  id: "Id",
  password: "Password",
};

export const roleMapping: Record<string, string> = {
  true: "Active",
  false: "Inactive",
  ROLE_EMPLOYEE: "Employee",
  ROLE_ADMIN: "Admin",
  ROLE_MANAGER: "Manager",
};
