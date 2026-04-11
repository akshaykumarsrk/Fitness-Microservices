const Navbar = ({ logOut }) => {
  return (
    <div className="flex justify-between items-center px-6 py-4 
                    bg-black/40 backdrop-blur-lg border-b border-white/10">

      <h1 className="text-2xl font-bold text-white tracking-wide">
        🚀 Fitness AI
      </h1>

      <button
        onClick={logOut}
        className="bg-gradient from-red-500 to-pink-500 
                   px-5 py-2 rounded-lg text-white font-medium 
                   hover:opacity-90 transition shadow-md"
      >
        Logout
      </button>
    </div>
  );
};

export default Navbar;