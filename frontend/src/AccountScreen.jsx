import { useEffect, useState } from 'react';

export default function AccountScreen({ user }) {
  const [myAccounts, setMyAccounts] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (user) {
      // Hits your specific endpoint: /api/customers/accounts/name/bobby
//       fetch(`http://localhost:8080/api/customers/accounts/name/${user.username}`)
fetch(`https://your-spring-backend.onrender.com/api/accounts/name/${user.username}`)
        .then(res => res.json())
        .then(data => {
          setMyAccounts(data);
          setLoading(false);
        })
        .catch(() => setLoading(false));
    }
  }, [user]);

  if (loading) return <p>Loading your financial accounts...</p>;

  return (
    <div style={{ maxWidth: '600px' }}>
      <h3>💳 Your Linked Bank Accounts</h3>
      {myAccounts.length > 0 ? (
        <div style={{ display: 'flex', flexDirection: 'column', gap: '15px' }}>
          {myAccounts.map(account => (
            <div key={account.id} style={{ padding: '20px', border: '1px solid #2e7d32', borderRadius: '8px', backgroundColor: '#e8f5e9' }}>
              <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                <strong>{account.accountType}</strong>
                <span style={{ color: 'gray', fontSize: '12px' }}>Ref: {account.id}</span>
              </div>
              <h2 style={{ margin: '10px 0 0 0', color: '#1b5e20' }}>
                ${account.balance.toLocaleString(undefined, { minimumFractionDigits: 2, maximumFractionDigits: 2 })}
              </h2>
            </div>
          ))}
        </div>
      ) : (
        <p style={{ color: 'gray', fontStyle: 'italic' }}>You do not have any active bank accounts registered to your profile.</p>
      )}
    </div>
  );
}
// import { useEffect, useState } from 'react';
//
// export default function AccountScreen() {
//   const [accounts, setAccounts] = useState([]);
//   // Tracks the currently selected account for the details panel
//   const [selectedAccount, setSelectedAccount] = useState(null);
//
//   useEffect(() => {
//     fetch('http://localhost:8080/api/customers/accounts')
//       .then(res => res.json())
//       .then(data => setAccounts(data));
//   }, []);
//
//   return (
//     <div style={{ display: 'flex', gap: '40px', marginTop: '20px' }}>
//
//       {/* LEFT SIDE: The Account Button List (Master) */}
//       <div style={{ flex: 1 }}>
//         <h3>Global Accounts Ledger</h3>
//         <div style={{ display: 'flex', flexDirection: 'column', gap: '10px' }}>
//           {accounts.map(account => (
//             <button
//               key={account.id}
//               onClick={() => setSelectedAccount(account)} // Updates the panel when clicked
//               style={{
//                 padding: '12px',
//                 textAlign: 'left',
//                 backgroundColor: selectedAccount?.id === account.id ? '#e6f4ea' : '#f8f9fa',
//                 border: '1px solid #ccc',
//                 borderRadius: '6px',
//                 cursor: 'pointer',
//                 fontWeight: 'bold',
//                 fontSize: '16px'
//               }}
//             >
//               💳 Account Ref ID: {account.id}
//             </button>
//           ))}
//         </div>
//       </div>
//
//       {/* RIGHT SIDE: The Account Data Inspector (Detail) */}
//       <div style={{ flex: 1, padding: '20px', border: '1px dashed #ccc', borderRadius: '8px', minHeight: '200px' }}>
//         <h3>Account Data Inspector</h3>
//
//         {selectedAccount ? (
//           // If an account is clicked, show its internal fields
//           <div>
//             <div style={{ fontSize: '32px', marginBottom: '10px' }}>
//               {selectedAccount.accountType === 'SavingsAccount' ? '🐷' : 'Checking' ? '🏦' : '💰'}
//             </div>
//
//             <p><strong>Account Reference ID:</strong> {selectedAccount.id}</p>
//             <p><strong>Account Classification:</strong> {selectedAccount.accountType}</p>
//
//             <p style={{ fontSize: '18px', marginTop: '15px' }}>
//               <strong>Current Ledger Balance: </strong>
//               <span style={{
//                 color: selectedAccount.balance >= 0 ? '#2e7d32' : '#d32f2f',
//                 fontWeight: 'bold'
//               }}>
//                 ${selectedAccount.balance.toLocaleString(undefined, { minimumFractionDigits: 2, maximumFractionDigits: 2 })}
//               </span>
//             </p>
//
//             <button
//               onClick={() => setSelectedAccount(null)}
//               style={{
//                 marginTop: '25px',
//                 color: 'gray',
//                 border: 'none',
//                 background: 'none',
//                 cursor: 'pointer',
//                 textDecoration: 'underline'
//               }}
//             >
//               Close Ledger View
//             </button>
//           </div>
//         ) : (
//           // Default placeholder message when no buttons are active
//           <p style={{ color: '#888', fontStyle: 'italic' }}>
//             Select an account reference number from the ledger to inspect its balances and traits.
//           </p>
//         )}
//       </div>
//
//     </div>
//   );
// }