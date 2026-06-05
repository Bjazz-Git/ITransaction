import { useEffect, useState } from 'react';

export default function AdminCustomerScreen() {
  const [customers, setCustomers] = useState([]);
  const [selectedCustomer, setSelectedCustomer] = useState(null);

  // Form states for creating/editing customers
  const [isEditing, setIsEditing] = useState(false);
  const [editForm, setEditForm] = useState({ name: '', username: '', password: '' });
  const [newCustomerForm, setNewCustomerForm] = useState({ name: '', username: '', password: '' });

  // Form states for creating a new account for the selected customer
  const [newAccountForm, setNewAccountForm] = useState({ accountType: 'CheckingAccount', balance: 0 });

  // GET ALL CUSTOMERS
  useEffect(() => {
    fetch(`https://itransaction.onrender.com/api/customers`)
      .then(res => res.json())
      .then(data => setCustomers(data))
      .catch(err => console.error("Error fetching global customers:", err));
  }, []);

// CREATE A NEW CUSTOMER
const handleCreateCustomer = (e) => {
  e.preventDefault();

  // 🟢 Pass the form data exactly as the user typed it. Backend assigns the sequential ID.
  fetch(`https://itransaction.onrender.com/api/customers`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(newCustomerForm)
  })
  .then(res => {
    if (!res.ok) {
      throw new Error("Server error during customer onboarding");
    }
    return res.json();
  })
  .then(newCustomer => {
    // Append the newly created customer (which now includes its backend-assigned ID) to your UI state
    setCustomers([...customers, newCustomer]);
    // Reset form inputs
    setNewCustomerForm({ name: '', username: '', password: '' });
    alert("Customer created successfully!");
  })
  .catch(err => {
    console.error("Error creating customer:", err);
    alert("Onboarding failed. Check backend logs for sequence tracking sync.");
  });
};

  // UPDATE AN EXISTING CUSTOMER
  const handleUpdateCustomer = (e) => {
    e.preventDefault();

    // Build a complete payload containing the ID to prevent Spring Boot deserialization errors
    const updatedPayload = {
      id: selectedCustomer.id,
      name: editForm.name,
      username: editForm.username,
      password: editForm.password
    };

    fetch(`https://itransaction.onrender.com/api/customers/${selectedCustomer.id}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(updatedPayload) // 🟢 Fix: Send the complete object instead of just editForm
    })
    .then(() => {
      // Create the updated customer object for local state sync
      const updated = { ...selectedCustomer, ...editForm };
      setCustomers(customers.map(c => c.id === selectedCustomer.id ? updated : c));
      setSelectedCustomer(updated);
      setIsEditing(false);
      alert("Customer records updated.");
    })
    .catch(err => console.error(err));
  };

  // DELETE A CUSTOMER
  const handleDeleteCustomer = (id) => {
    if (window.confirm("CRITICAL WARNING: Are you certain you want to purge this customer? All data configurations will be cleared.")) {
      fetch(`https://itransaction.onrender.com/api/customers/${id}`, {
        method: 'DELETE'
      })
      .then(() => {
        setCustomers(customers.filter(c => c.id !== id));
        setSelectedCustomer(null);
        alert("Customer successfully terminated.");
      })
      .catch(err => console.error(err));
    }
  };

  // CREATE AN ACCOUNT FOR SELECTED CUSTOMER
  const handleCreateAccount = (e) => {
    e.preventDefault();
    fetch(`https://itransaction.onrender.com/api/customers/id/${selectedCustomer.id}/accounts`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(newAccountForm)
    })
    .then(() => {
      alert("Account assigned successfully! Refreshing database view...");
      // Re-fetch global customers list to update nested accounts array visually
      return fetch(`https://itransaction.onrender.com/api/customers`);
    })
    .then(res => res.json())
    .then(data => {
      setCustomers(data);
      const freshlyUpdatedCustomer = data.find(c => c.id === selectedCustomer.id);
      setSelectedCustomer(freshlyUpdatedCustomer);
      setNewAccountForm({ accountType: 'CheckingAccount', balance: 0 });
    })
    .catch(err => console.error(err));
  };

  const startEditing = () => {
    setEditForm({
      name: selectedCustomer.name,
      username: selectedCustomer.username,
      password: selectedCustomer.password
    });
    setIsEditing(true);
  };

  return (
    <div style={{ display: 'flex', gap: '40px', marginTop: '20px', fontFamily: 'sans-serif' }}>

      {/* LEFT SIDE: Directory and Creation Form */}
      <div style={{ flex: 1 }}>
        <h3>Global Customer Directory (Admin View)</h3>
        <div style={{ display: 'flex', flexDirection: 'column', gap: '10px', marginBottom: '30px' }}>
          {customers.map(customer => (
            <button
              key={customer.id}
              onClick={() => { setSelectedCustomer(customer); setIsEditing(false); }}
              style={{
                padding: '12px',
                textAlign: 'left',
                backgroundColor: selectedCustomer?.id === customer.id ? '#fff3cd' : '#f8f9fa',
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

        {/* Form to Onboard a Brand New Customer */}
        <div style={{ padding: '20px', border: '1px solid #ddd', borderRadius: '8px', backgroundColor: '#fafafa' }}>
          <h4>➕ Onboard New Customer Profile</h4>
          <form onSubmit={handleCreateCustomer} style={{ display: 'flex', flexDirection: 'column', gap: '10px' }}>
            <input type="text" placeholder="Full Legal Name" required value={newCustomerForm.name} onChange={e => setNewCustomerForm({...newCustomerForm, name: e.target.value})} style={{padding: '8px'}} />
            <input type="text" placeholder="Desired Username" required value={newCustomerForm.username} onChange={e => setNewCustomerForm({...newCustomerForm, username: e.target.value})} style={{padding: '8px'}} />
            <input type="password" placeholder="Temporary Security Password" required value={newCustomerForm.password} onChange={e => setNewCustomerForm({...newCustomerForm, password: e.target.value})} style={{padding: '8px'}} />
            <button type="submit" style={{ backgroundColor: '#28a745', color: 'white', padding: '10px', border: 'none', borderRadius: '4px', cursor: 'pointer', fontWeight: 'bold' }}>Register Profile</button>
          </form>
        </div>
      </div>

      {/* RIGHT SIDE: Deep Profile Inspector and Nested Adjustments */}
      <div style={{ flex: 1, padding: '20px', border: '2px dashed #ffc107', borderRadius: '8px', minHeight: '200px', backgroundColor: '#fffdf5' }}>
        <h3>System Profile Audit</h3>

        {selectedCustomer ? (
          <div>
            {!isEditing ? (
              /* Static Audit Detail Block View */
              <div>
                <p><strong>Database ID Field:</strong> {selectedCustomer.id}</p>
                <p><strong>Legal Full Name:</strong> {selectedCustomer.name}</p>
                <p><strong>Account Username:</strong> {selectedCustomer.username}</p>
                <p><strong>Raw Password Key:</strong> <code style={{color: 'red', backgroundColor: '#fee', padding: '2px 4px', borderRadius: '4px'}}>{selectedCustomer.password}</code></p>

                <button onClick={startEditing} style={{ marginRight: '10px', padding: '6px 12px', cursor: 'pointer' }}>✏️ Edit Metadata</button>
                <button onClick={() => handleDeleteCustomer(selectedCustomer.id)} style={{ backgroundColor: '#dc3545', color: 'white', border: 'none', padding: '6px 12px', borderRadius: '4px', cursor: 'pointer' }}>🚨 Delete Profile</button>
              </div>
            ) : (
              /* Live Record Modification Input View */
              <form onSubmit={handleUpdateCustomer} style={{ display: 'flex', flexDirection: 'column', gap: '10px', margin: '15px 0' }}>
                <h4>Modify Profile Properties</h4>
                <label>Legal Name: <input type="text" value={editForm.name} onChange={e => setEditForm({...editForm, name: e.target.value})} style={{width: '100%', padding: '6px'}} /></label>
                <label>Username: <input type="text" value={editForm.username} onChange={e => setEditForm({...editForm, username: e.target.value})} style={{width: '100%', padding: '6px'}} /></label>
                <label>Password Update: <input type="text" value={editForm.password} onChange={e => setEditForm({...editForm, password: e.target.value})} style={{width: '100%', padding: '6px', color: 'red'}} /></label>
                <div>
                  <button type="submit" style={{ backgroundColor: '#007bff', color: 'white', padding: '6px 12px', border: 'none', borderRadius: '4px', marginRight: '10px', cursor: 'pointer' }}>Save Overwrites</button>
                  <button type="button" onClick={() => setIsEditing(false)} style={{ padding: '6px 12px', cursor: 'pointer' }}>Cancel</button>
                </div>
              </form>
            )}

            <hr style={{ margin: '20px 0', border: '0', borderTop: '1px solid #ffc107' }} />

            <h4>Linked Bank Assets:</h4>
            {selectedCustomer.accounts && selectedCustomer.accounts.length > 0 ? (
              <ul style={{ marginBottom: '20px' }}>
                {selectedCustomer.accounts.map(acc => (
                  <li key={acc.id} style={{ marginBottom: '5px' }}>
                    <strong>{acc.accountType}:</strong> ${acc.balance.toLocaleString()} <small style={{color:'gray'}}>(Ref: {acc.id})</small>
                  </li>
                ))}
              </ul>
            ) : (
              <p style={{ color: 'gray', fontStyle: 'italic' }}>No active asset tokens linked to this profile.</p>
            )}

            {/* Form to Assign a Brand New Asset Account to this Specific Selected Customer */}
            <div style={{ padding: '15px', border: '1px solid #ffc107', borderRadius: '6px', backgroundColor: '#fff9e6' }}>
              <h5>💼 Authorize New Asset Allocation</h5>
              <form onSubmit={handleCreateAccount} style={{ display: 'flex', gap: '10px', alignItems: 'center' }}>
                <select value={newAccountForm.accountType} onChange={e => setNewAccountForm({...newAccountForm, accountType: e.target.value})} style={{padding: '6px'}}>
                  <option value="CheckingAccount">Checking Account</option>
                  <option value="SavingsAccount">Savings Account</option>
                  <option value="InvestmentAccount">Investment Account</option>
                </select>
                <input type="number" placeholder="Opening Deposit ($)" required value={newAccountForm.balance || ''} onChange={e => setNewAccountForm({...newAccountForm, balance: parseFloat(e.target.value) || 0})} style={{padding: '6px', width: '130px'}} />
                <button type="submit" style={{ backgroundColor: '#ffc107', border: '1px solid #d39e00', padding: '6px 12px', borderRadius: '4px', cursor: 'pointer', fontWeight: 'bold' }}>Provision</button>
              </form>
            </div>

            <button
              onClick={() => { setSelectedCustomer(null); setIsEditing(false); }}
              style={{ marginTop: '25px', color: '#666', cursor: 'pointer', fontWeight: 'bold', background: 'none', border: '1px solid #ccc', padding: '5px 10px', borderRadius: '4px' }}
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