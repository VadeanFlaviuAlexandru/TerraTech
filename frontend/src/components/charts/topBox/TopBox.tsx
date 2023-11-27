import "./topBox.scss";

type Props = {
  topDealUsers: {
    firstName: string;
    email: string;
    amount: number;
  }[];
};

const TopBox = (props: Props) => {
  return (
    <div className="topBox">
      <h1>Top Deals</h1>
      <div className="list">
        {props.topDealUsers.map((user, index) => (
          <div className="listItem" key={index}>
            <div className="user">
              <div className="userTexts">
                <span className="username">{user?.firstName}</span>
                <span className="email">{user?.email}</span>
              </div>
            </div>
            <span className="amount">${user?.amount}</span>
          </div>
        ))}
      </div>
    </div>
  );
};

export default TopBox;
