import Layout from "../components/Layout";
import ActivityForm from "../components/ActivityForm";
import ActivityList from "../components/ActivityList";

const Dashboard = ({ logOut }) => {
  return (
    <Layout logOut={logOut}>
      <div className="max-w-6xl mx-auto space-y-8">

        {/* Heading */}
        <div className="text-center">
          <h1 className="text-4xl font-bold text-white">
            💪 Fitness Dashboard
          </h1>
          <p className="text-gray-400">
            Track your daily activities with AI insights
          </p>
        </div>

        {/* Form */}
        <ActivityForm />

        {/* List */}
        <ActivityList />
      </div>
    </Layout>
  );
};

export default Dashboard;