import { useEffect, useState } from 'react';

export default function CustomerScreen({ user }) {
  if (!user) return <p>Loading profile...</p>;

  return (
    <div style={{ maxWidth: '500px', padding: '20px', border: '1px solid #ccc', borderRadius: '8px', backgroundColor: '#f9f9f9' }}>
      <h3>🔒 Your Personal Security Profile</h3>
      <p><strong>Customer ID:</strong> {user.id}</p>
      <p><strong>Full Name:</strong> {user.name}</p>
      <p><strong>Username:</strong> {user.username}</p>
      <p><strong>Password:</strong> <code style={{ color: 'red' }}>{user.password}</code></p>
      <small style={{ color: 'gray' }}>If any of this data is incorrect, please contact an administrator.</small>
    </div>
  );
}

// export default function CustomerScreen() {
//   const [customers, setCustomers] = useState([]);
//   // This state tracks which specific customer object is active/clicked
//   const [selectedCustomer, setSelectedCustomer] = useState(null);
//
//   useEffect(() => {
//     fetch('http://localhost:8080/api/customers')
//       .then(res => res.json())
//       .then(data => setCustomers(data));
//   }, []);
//
//   return (
//     <div style={{ display: 'flex', gap: '40px', marginTop: '20px' }}>
//
//       {/* LEFT SIDE: The Button List (Master) */}
//       <div style={{ flex: 1 }}>
//         <h3>Customer Directory</h3>
//         <div style={{ display: 'flex', flexDirection: 'column', gap: '10px' }}>
//           {customers.map(customer => (
//             <button
//               key={customer.id}
//               onClick={() => setSelectedCustomer(customer)} // Updates state with the clicked object
//               style={{
//                 padding: '12px',
//                 textAlign: 'left',
//                 backgroundColor: selectedCustomer?.id === customer.id ? '#e0f0ff' : '#f8f9fa',
//                 border: '1px solid #ccc',
//                 borderRadius: '6px',
//                 cursor: 'pointer',
//                 fontWeight: 'bold',
//                 fontSize: '16px'
//               }}
//             >
//               👤 {customer.name} (ID: {customer.id})
//             </button>
//           ))}
//         </div>
//       </div>
//
//       {/* RIGHT SIDE: The Data Inspector (Detail) */}
//       <div style={{ flex: 1, padding: '20px', border: '1px dashed #ccc', borderRadius: '8px', minHeight: '200px' }}>
//         <h3>Customer Data Inspector</h3>
//
//         {selectedCustomer ? (
//           // If a customer is clicked, display all their data!
//           <div>
//             <p><strong>Database ID:</strong> {selectedCustomer.id}</p>
//             <p><strong>Full Name:</strong> {selectedCustomer.name}</p>
//             <p><strong>Username:</strong> {selectedCustomer.username}</p>
//             <p><strong>System Password:</strong> <code style={{color: 'red'}}>{selectedCustomer.password}</code></p>
//
//             <h4>Linked Bank Accounts:</h4>
//             {selectedCustomer.accounts && selectedCustomer.accounts.length > 0 ? (
//               <ul>
//                 {selectedCustomer.accounts.map(acc => (
//                   <li key={acc.id}>
//                     <strong>{acc.accountType}:</strong> ${acc.balance} <small>(Ref: {acc.id})</small>
//                   </li>
//                 ))}
//               </ul>
//             ) : (
//               <p style={{ color: 'gray' }}>No accounts linked to this user.</p>
//             )}
//
//             <button
//               onClick={() => setSelectedCustomer(null)}
//               style={{ marginTop: '15px', color: 'gray', border: 'none', background: 'none', cursor: 'pointer', textDecoration: 'underline' }}
//             >
//               Clear Inspector
//             </button>
//           </div>
//         ) : (
//           // Default placeholder state if nothing has been clicked yet
//           <p style={{ color: '#888', fontStyle: 'italic' }}>
//             Click on a customer button on the left to inspect their deep data profile.
//           </p>
//         )}
//       </div>
//
//     </div>
//   );
// }