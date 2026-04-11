import { useState } from "react";
import { addActivity } from "../services/api";
import { motion } from "framer-motion";
import { Flame, Timer, Activity } from "lucide-react";
import toast from "react-hot-toast";

const ActivityForm = ({ refresh }) => {
  const [data, setData] = useState({
    type: "RUNNING",
    duration: "",
    caloriesBurned: "",
  });

  const [errors, setErrors] = useState({});
  const [loading, setLoading] = useState(false);

  // ✅ Validation
  const validate = () => {
    const newErrors = {};

    if (!data.duration || data.duration <= 0) {
      newErrors.duration = "Duration must be greater than 0";
    }

    if (!data.caloriesBurned || data.caloriesBurned <= 0) {
      newErrors.caloriesBurned = "Calories must be greater than 0";
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  // ✅ Submit
  const submit = async (e) => {
    e.preventDefault();

    if (!validate()) return;

    try {
      setLoading(true);

      await addActivity(data);

      toast.success("Activity added successfully 🚀");

      refresh();

      setData({
        type: "RUNNING",
        duration: "",
        caloriesBurned: "",
      });

      setErrors({});
    } catch (err) {
      toast.error("Something went wrong ❌");
    } finally {
      setLoading(false);
    }
  };

  return (
    <motion.form
      initial={{ opacity: 0, y: 30 }}
      animate={{ opacity: 1, y: 0 }}
      onSubmit={submit}
      className="bg-gradient from-white/10 to-white/5 
           backdrop-blur-xl border border-white/10 
           p-6 rounded-2xl shadow-2xl"
    >
      <h2 className="text-2xl font-semibold mb-4 text-white flex items-center gap-2">
        <Activity /> Add Activity
      </h2>

      {/* Activity Type */}
      <label className="text-sm text-gray-300">Activity Type</label>
      <select
        value={data.type}
        onChange={(e) => setData({ ...data, type: e.target.value })}
        className="w-full mb-4 mt-1 p-3 rounded-lg bg-black/40 text-white border border-white/20 focus:ring-2 focus:ring-blue-500"
      >
        <option value="RUNNING">🏃 Running</option>
        <option value="WALKING">🚶 Walking</option>
        <option value="CYCLING">🚴 Cycling</option>
      </select>

      {/* Duration */}
      <label className="text-sm text-gray-300 flex items-center gap-2">
        <Timer size={16} /> Duration (minutes)
      </label>
      <input
        type="number"
        value={data.duration}
        placeholder="Enter duration"
        onChange={(e) =>
          setData({ ...data, duration: e.target.value })
        }
        className={`w-full mt-1 p-3 rounded-lg bg-black/40 text-white border 
        ${errors.duration ? "border-red-500" : "border-white/20"}
        focus:ring-2 focus:ring-green-500`}
      />
      {errors.duration && (
        <p className="text-red-400 text-sm mt-1">
          {errors.duration}
        </p>
      )}

      {/* Calories */}
      <label className="text-sm text-gray-300 flex items-center gap-2 mt-4">
        <Flame size={16} /> Calories Burned
      </label>
      <input
        type="number"
        value={data.caloriesBurned}
        placeholder="Enter calories"
        onChange={(e) =>
          setData({ ...data, caloriesBurned: e.target.value })
        }
        className={`w-full mt-1 p-3 rounded-lg bg-black/40 text-white border 
        ${errors.caloriesBurned ? "border-red-500" : "border-white/20"}
        focus:ring-2 focus:ring-orange-500`}
      />
      {errors.caloriesBurned && (
        <p className="text-red-400 text-sm mt-1">
          {errors.caloriesBurned}
        </p>
      )}

      {/* Button */}
      <motion.button
        whileTap={{ scale: 0.95 }}
        disabled={loading}
        className={`w-full mt-6 py-3 rounded-lg font-semibold text-white 
        bg-gradient from-blue-500 via-purple-500 to-pink-500 
        flex items-center justify-center gap-2 bg-amber-700 cursor-pointer  hover:bg-amber-600
        ${loading ? "opacity-60 cursor-not-allowed" : "hover:opacity-90"}`}
      >
        {loading ? (
          <>
            <div className="w-5 h-5 border-2 border-white border-t-transparent rounded-full animate-spin"></div>
            Adding...
          </>
        ) : (
          "🚀 Add Activity"
        )}
      </motion.button>
    </motion.form>
  );
};

export default ActivityForm;