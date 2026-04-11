import { Home, Activity } from "lucide-react";
import { useNavigate } from "react-router";

const Sidebar = () => {
  const navigate = useNavigate();

  return (
    <div className="w-64 bg-black/40 backdrop-blur-xl p-5 border-r border-white/10">
      <h1 className="text-2xl font-bold mb-10">🏋️ Fitness AI</h1>

      <div className="space-y-4">
        <button
          onClick={() => navigate("/activities")}
          className="flex items-center gap-3 hover:text-blue-400"
        >
          <Home /> Dashboard
        </button>

        <button className="flex items-center gap-3 hover:text-blue-400">
          <Activity /> Activities
        </button>
      </div>
    </div>
  );
};

export default Sidebar;