import { useState } from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import CustomerScreen from './CustomerScreen';
import AccountScreen from './AccountScreen';
import LoginScreen from './LoginScreen';

// 1. Import your old master-detail screens or keep them under new names if you split them.
// For this example, let's assume we created specialized components for the admin view:
import AdminCustomerScreen from './AdminCustomerScreen';
import AdminAccountScreen from './AdminAccountScreen';

function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [loggedInUser, setLoggedInUser] = useState(null);
  const [isAdmin, setIsAdmin] = useState(false); // 👈 Track admin status

  if (!isAuthenticated) {
    return (
      <LoginScreen
        onLoginSuccess={(userData) => {
          setIsAuthenticated(true);
          setLoggedInUser(userData);

          // Check if the credentials belong to your designated admin account
          if (userData.username === 'admin') {
            setIsAdmin(true);
          } else {
            setIsAdmin(false);
          }
        }}
      />
    );
  }

  return (
    <Router>
      <div style={{ padding: '20px', fontFamily: 'sans-serif' }}>
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
          <h1>{isAdmin ? "⚠️ Admin Management Console" : `Welcome back, ${loggedInUser?.name}!`}</h1>
          <button
            onClick={() => { setIsAuthenticated(false); setLoggedInUser(null); setIsAdmin(false); }}
            style={{ padding: '6px 12px', background: '#f44336', color: 'white', border: 'none', borderRadius: '4px', cursor: 'pointer' }}
          >
            Logout
          </button>
        </div>

        {/* Navigation Bar changes dynamically depending on who is logged in */}
        <nav style={{ marginBottom: '20px', display: 'flex', gap: '15px', marginTop: '10px' }}>
          {isAdmin ? (
            <>
              <Link to="/admin/customers" style={navStyle}>Global Customers</Link>
              <Link to="/admin/accounts" style={navStyle}>Global Accounts Ledger</Link>
            </>
          ) : (
            <>
              <Link to="/profile" style={navStyle}>My Profile</Link>
              <Link to="/accounts" style={navStyle}>My Accounts</Link>
            </>
          )}
        </nav>

        <hr />

        <Routes>
          {isAdmin ? (
            /* 🔓 ADMIN ROUTES: Renders the global lists with button sidebars */
            <>
              <Route path="/" element={<AdminCustomerScreen />} />
              <Route path="/admin/customers" element={<AdminCustomerScreen />} />
              <Route path="/admin/accounts" element={<AdminAccountScreen />} />
            </>
          ) : (
            /* 🔒 USER ROUTES: Renders isolated data screens */
            <>
              <Route path="/" element={<CustomerScreen user={loggedInUser} />} />
              <Route path="/profile" element={<CustomerScreen user={loggedInUser} />} />
              <Route path="/accounts" element={<AccountScreen user={loggedInUser} />} />
            </>
          )}
        </Routes>
      </div>
    </Router>
  );
}

const navStyle = { textDecoration: 'none', color: '#007bff', fontWeight: 'bold', padding: '8px 16px', border: '1px solid #007bff', borderRadius: '4px' };

export default App;
// export default App;
// import { useState } from 'react'
// import reactLogo from './assets/react.svg'
// import viteLogo from './assets/vite.svg'
// import heroImg from './assets/hero.png'
// import './App.css'
//
// function App() {
//   const [count, setCount] = useState(0)
//
//   return (
//     <>
//       <section id="center">
//         <div className="hero">
//           <img src={heroImg} className="base" width="170" height="179" alt="" />
//           <img src={reactLogo} className="framework" alt="React logo" />
//           <img src={viteLogo} className="vite" alt="Vite logo" />
//         </div>
//         <div>
//           <h1>Get started</h1>
//           <p>
//             Edit <code>src/App.jsx</code> and save to test <code>HMR</code>
//           </p>
//         </div>
//         <button
//           type="button"
//           className="counter"
//           onClick={() => setCount((count) => count + 1)}
//         >
//           Count is {count}
//         </button>
//       </section>
//
//       <div className="ticks"></div>
//
//       <section id="next-steps">
//         <div id="docs">
//           <svg className="icon" role="presentation" aria-hidden="true">
//             <use href="/icons.svg#documentation-icon"></use>
//           </svg>
//           <h2>Documentation</h2>
//           <p>Your questions, answered</p>
//           <ul>
//             <li>
//               <a href="https://vite.dev/" target="_blank">
//                 <img className="logo" src={viteLogo} alt="" />
//                 Explore Vite
//               </a>
//             </li>
//             <li>
//               <a href="https://react.dev/" target="_blank">
//                 <img className="button-icon" src={reactLogo} alt="" />
//                 Learn more
//               </a>
//             </li>
//           </ul>
//         </div>
//         <div id="social">
//           <svg className="icon" role="presentation" aria-hidden="true">
//             <use href="/icons.svg#social-icon"></use>
//           </svg>
//           <h2>Connect with us</h2>
//           <p>Join the Vite community</p>
//           <ul>
//             <li>
//               <a href="https://github.com/vitejs/vite" target="_blank">
//                 <svg
//                   className="button-icon"
//                   role="presentation"
//                   aria-hidden="true"
//                 >
//                   <use href="/icons.svg#github-icon"></use>
//                 </svg>
//                 GitHub
//               </a>
//             </li>
//             <li>
//               <a href="https://chat.vite.dev/" target="_blank">
//                 <svg
//                   className="button-icon"
//                   role="presentation"
//                   aria-hidden="true"
//                 >
//                   <use href="/icons.svg#discord-icon"></use>
//                 </svg>
//                 Discord
//               </a>
//             </li>
//             <li>
//               <a href="https://x.com/vite_js" target="_blank">
//                 <svg
//                   className="button-icon"
//                   role="presentation"
//                   aria-hidden="true"
//                 >
//                   <use href="/icons.svg#x-icon"></use>
//                 </svg>
//                 X.com
//               </a>
//             </li>
//             <li>
//               <a href="https://bsky.app/profile/vite.dev" target="_blank">
//                 <svg
//                   className="button-icon"
//                   role="presentation"
//                   aria-hidden="true"
//                 >
//                   <use href="/icons.svg#bluesky-icon"></use>
//                 </svg>
//                 Bluesky
//               </a>
//             </li>
//           </ul>
//         </div>
//       </section>
//
//       <div className="ticks"></div>
//       <section id="spacer"></section>
//     </>
//   )
// }
//
// export default App
