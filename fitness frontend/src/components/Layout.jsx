import Navbar from "./Navbar";

const Layout = ({ children, logOut }) => {
  return (
    <div className="relative min-h-screen overflow-hidden">

      {/* 🔥 Background Glow Effects */}
      <div className="absolute top-[-100px] left-[-100px] w-[400px] h-[400px] bg-purple-600 opacity-30 blur-3xl rounded-full"></div>
      <div className="absolute bottom-[-100px] right-[-100px] w-[400px] h-[400px] bg-blue-600 opacity-30 blur-3xl rounded-full"></div>

      {/* Content */}
      <div className="relative z-10">
        <Navbar logOut={logOut} />
        <div className="p-6">{children}</div>
      </div>
    </div>
  );
};

export default Layout;