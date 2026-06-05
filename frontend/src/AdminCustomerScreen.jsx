import { useEffect, useState } from 'react';

export default function AdminCustomerScreen() {
  const [customers, setCustomers] = useState([]);
  const [selectedCustomer, setSelectedCustomer] = useState(null);

  useEffect(() => {
    // Queries the global customer directory endpoint
    fetch('http://localhost:8080/api/customers')
      .then(res => res.json())
      .then(data => setCustomers(data))
      .catch(err => console.error("Error fetching global customers:", err));
  }, []);

  return (
    <div style={{ display: 'flex', gap: '40px', marginTop: '20px' }}>

      {/* LEFT SIDE: Admin Master Control List */}
      <div style={{ flex: 1 }}>
        <h3>Global Customer Directory (Admin View)</h3>
        <div style={{ display: 'flex', flexDirection: 'column', gap: '10px' }}>
          {customers.map(customer => (
            <button
              key={customer.id}
              onClick={() => setSelectedCustomer(customer)}
              style={{
                padding: '12px',
                textAlign: 'left',
                backgroundColor: selectedCustomer?.id === customer.id ? '#fff3cd' : '#f8f9fa', // Warning/Gold tint for admin selections
                border: '1px solid #ffc107',
                borderRadius: '6px',
                cursor: 'pointer',
                fontWeight: 'bold',
                fontSize: '16px'
              }}
            >
              👥 {customer.name} (ID: {customer.id})
            </button>
          ))}
        </div>
      </div>

      {/* RIGHT SIDE: Deep Profile Inspector */}
      <div style={{ flex: 1, padding: '20px', border: '2px dashed #ffc107', borderRadius: '8px', minHeight: '200px', backgroundColor: '#fffdf5' }}>
        <h3>System Profile Audit</h3>

        {selectedCustomer ? (
          <div>
            <p><strong>Database ID Field:</strong> {selectedCustomer.id}</p>
            <p><strong>Legal Full Name:</strong> {selectedCustomer.name}</p>
            <p><strong>Account Username:</strong> {selectedCustomer.username}</p>
            <p><strong>Raw Password Key:</strong> <code style={{color: 'red', backgroundColor: '#fee', padding: '2px 4px', borderRadius: '4px'}}>{selectedCustomer.password}</code></p>

            <h4>Linked Bank Assets:</h4>
            {selectedCustomer.accounts && selectedCustomer.accounts.length > 0 ? (
              <ul>
                {selectedCustomer.accounts.map(acc => (
                  <li key={acc.id}>
                    <strong>{acc.accountType}:</strong> ${acc.balance.toLocaleString()} <small style={{color:'gray'}}>(Ref: {acc.id})</small>
                  </li>
                ))}
              </ul>
            ) : (
              <p style={{ color: 'gray', fontStyle: 'italic' }}>No active asset tokens linked to this profile.</p>
            )}

            <button
              onClick={() => setSelectedCustomer(null)}
              style={{ marginTop: '20px', color: '#666', cursor: 'pointer', fontWeight: 'bold' }}
            >
              Close Record Audit
            </button>
          </div>
        ) : (
          <p style={{ color: '#856404', fontStyle: 'italic' }}>
            Select an institutional user block from the active directory map to view their credential keys and linked ledgers.
          </p>
        )}
      </div>

    </div>
  );
}