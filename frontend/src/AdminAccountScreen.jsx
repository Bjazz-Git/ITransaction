import { useEffect, useState } from 'react';

export default function AdminAccountScreen() {
  const [accounts, setAccounts] = useState([]);
  const [selectedAccount, setSelectedAccount] = useState(null);

  // Search states
  const [searchQuery, setSearchQuery] = useState('');

  // Editing state for updating balances/types
  const [isEditing, setIsEditing] = useState(false);
  const [editForm, setEditForm] = useState({ accountType: 'CheckingsAccount', balance: 0 });

  // Creation state for a standalone new account mapping
  const [newAccountForm, setNewAccountForm] = useState({
    customerId: '',
    accountType: 'CheckingsAccount',
    balance: 0
  });

  // GET ALL ACCOUNTS ON INITIAL LOAD
  const fetchAllAccounts = () => {
    fetch(`https://itransaction.onrender.com/api/accounts`)
      .then(res => res.json())
      .then(data => setAccounts(data))
      .catch(err => console.error("Error fetching institutional ledgers:", err));
  };

  useEffect(() => {
    fetchAllAccounts();
  }, []);

  // UNIFIED SEARCH: BY ACCOUNT ID OR CUSTOMER NAME
  const handleSearch = (e) => {
    e.preventDefault();

    if (!searchQuery.trim()) {
      alert("Please enter an Account ID or Customer Name.");
      return;
    }

    const isNumeric = /^\d+$/.test(searchQuery.trim());
    let endpoint = `https://itransaction.onrender.com/api/accounts/search?name=${searchQuery.trim()}`;

    if (isNumeric) {
      endpoint = `https://itransaction.onrender.com/api/accounts/${searchQuery.trim()}`;
    }

    fetch(endpoint)
      .then(res => {
        if (!res.ok) throw new Error("No matching ledger accounts found.");
        return res.json();
      })
      .then(data => {
        // Handle if backend returns a single object (for ID) vs an array (for Name)
        const results = Array.isArray(data) ? data : [data];
        setAccounts(results);
        if (results.length === 1) {
          setSelectedAccount(results[0]);
        } else {
          setSelectedAccount(null);
        }
        setIsEditing(false);
      })
      .catch(err => {
        console.error(err);
        alert(`Search failed: ${err.message}`);
      });
  };

  // STANDALONE ACCOUNT CREATION (Maps account directly to an existing Customer ID)
  const handleCreateAccount = (e) => {
    e.preventDefault();

    if (!newAccountForm.customerId) {
      alert("A valid Customer ID link is required.");
      return;
    }

    fetch(`https://itransaction.onrender.com/api/customers/id/${newAccountForm.customerId}/accounts`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        accountType: newAccountForm.accountType,
        balance: newAccountForm.balance
      })
    })
    .then(res => {
      if (!res.ok) throw new Error("Failed to provision account asset tracking.");
      alert("Account created and mapped successfully!");
      setNewAccountForm({ customerId: '', accountType: 'CheckingsAccount', balance: 0 });
      fetchAllAccounts(); // Refresh directory view
    })
    .catch(err => {
      console.error(err);
      alert("Asset setup failed. Verify Customer ID exists in the database.");
    });
  };

  // UPDATE AN ACCOUNT BALANCE OR TYPE
  const handleUpdateAccount = (e) => {
    e.preventDefault();

    fetch(`https://itransaction.onrender.com/api/accounts/${selectedAccount.id}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(editForm)
    })
    .then(res => {
      if (!res.ok) throw new Error("Failed to write overwrite adjustments.");
      return res.json();
    })
    .then(updatedAccount => {
      setAccounts(accounts.map(acc => acc.id === selectedAccount.id ? updatedAccount : acc));
      setSelectedAccount(updatedAccount);
      setIsEditing(false);
      alert("Ledger records successfully synchronized.");
    })
    .catch(err => console.error(err));
  };

  const startEditing = () => {
    setEditForm({
      accountType: selectedAccount.accountType,
      balance: selectedAccount.balance
    });
    setIsEditing(true);
  };

  return (
    <div style={{ display: 'flex', gap: '40px', marginTop: '20px', fontFamily: 'sans-serif' }}>

      {/* LEFT SIDE: Directory and Search Controls */}
      <div style={{ flex: 1 }}>
        <h3>Global Asset Accounts (Admin Ledger)</h3>

        {/* Search Widget */}
        <div style={{ marginBottom: '20px', padding: '15px', border: '1px solid #17a2b8', borderRadius: '6px', backgroundColor: '#eef9fa' }}>
          <form onSubmit={handleSearch} style={{ display: 'flex', gap: '10px' }}>
            <input
              type="text"
              placeholder="Search by Account ID or Holder Name..."
              value={searchQuery}
              onChange={e => setSearchQuery(e.target.value)}
              style={{ padding: '8px', flex: 1, borderRadius: '4px', border: '1px solid #ccc' }}
            />
            <button type="submit" style={{ backgroundColor: '#17a2b8', color: 'white', border: 'none', padding: '8px 15px', borderRadius: '4px', cursor: 'pointer', fontWeight: 'bold' }}>
              🔍 Search
            </button>
            <button
              type="button"
              onClick={() => {
                setSearchQuery('');
                setSelectedAccount(null);
                fetchAllAccounts();
              }}
              style={{ background: '#ddd', border: 'none', padding: '8px 12px', borderRadius: '4px', cursor: 'pointer' }}
            >
              Reset
            </button>
          </form>
        </div>

        {/* Account Map Directory */}
        <div style={{ display: 'flex', flexDirection: 'column', gap: '10px', marginBottom: '30px' }}>
          {accounts.map(acc => (
            <button
              key={acc.id}
              onClick={() => { setSelectedAccount(acc); setIsEditing(false); }}
              style={{
                padding: '12px',
                textAlign: 'left',
                backgroundColor: selectedAccount?.id === acc.id ? '#d1ecf1' : '#f8f9fa',
                border: '1px solid #17a2b8',
                borderRadius: '6px',
                cursor: 'pointer',
                fontWeight: 'bold'
              }}
            >
              💳 {acc.accountType} (Ref: #{acc.id}) — Balance: ${acc.balance.toLocaleString()}
            </button>
          ))}
        </div>

        {/* Standalone Provision Form */}
        <div style={{ padding: '20px', border: '1px solid #ddd', borderRadius: '8px', backgroundColor: '#fafafa' }}>
          <h4>➕ Provision Independent Asset Account</h4>
          <form onSubmit={handleCreateAccount} style={{ display: 'flex', flexDirection: 'column', gap: '10px' }}>
            <input
              type="number"
              placeholder="Target Customer ID"
              required
              value={newAccountForm.customerId}
              onChange={e => setNewAccountForm({...newAccountForm, customerId: e.target.value})}
              style={{padding: '8px'}}
            />
            <select
              value={newAccountForm.accountType}
              onChange={e => setNewAccountForm({...newAccountForm, accountType: e.target.value})}
              style={{padding: '8px'}}
            >
              <option value="CheckingsAccount">Checking Account</option>
              <option value="SavingsAccount">Savings Account</option>
            </select>
            <input
              type="number"
              placeholder="Opening Balance ($)"
              required
              value={newAccountForm.balance || ''}
              onChange={e => setNewAccountForm({...newAccountForm, balance: parseFloat(e.target.value) || 0})}
              style={{padding: '8px'}}
            />
            <button type="submit" style={{ backgroundColor: '#17a2b8', color: 'white', padding: '10px', border: 'none', borderRadius: '4px', cursor: 'pointer', fontWeight: 'bold' }}>
              Authorize Asset Token
            </button>
          </form>
        </div>
      </div>

      {/* RIGHT SIDE: Deep Profile Inspector */}
      <div style={{ flex: 1, padding: '20px', border: '2px dashed #17a2b8', borderRadius: '8px', minHeight: '200px', backgroundColor: '#fcfdfe' }}>
        <h3>Ledger Balance Inspector</h3>

        {selectedAccount ? (
          <div>
            {!isEditing ? (
              <div>
                <p><strong>System Account Ref ID:</strong> #{selectedAccount.id}</p>
                <p><strong>Asset Classification:</strong> {selectedAccount.accountType}</p>
                <p><strong>Current Secure Balance:</strong> ${selectedAccount.balance.toLocaleString()}</p>

                <button onClick={startEditing} style={{ marginRight: '10px', padding: '6px 12px', cursor: 'pointer', fontWeight: 'bold' }}>
                  ✏️ Adjust Balance/Type
                </button>
              </div>
            ) : (
              <form onSubmit={handleUpdateAccount} style={{ display: 'flex', flexDirection: 'column', gap: '10px' }}>
                <h4>Modify Ledger Balances</h4>
                <label>Classification Type:
                  <select
                    value={editForm.accountType}
                    onChange={e => setEditForm({...editForm, accountType: e.target.value})}
                    style={{width: '100%', padding: '6px', marginTop: '4px'}}
                  >
                    <option value="CheckingsAccount">Checking Account</option>
                    <option value="SavingsAccount">Savings Account</option>
                  </select>
                </label>
                <label>Overwrite Balance Val ($):
                  <input
                    type="number"
                    value={editForm.balance}
                    onChange={e => setEditForm({...editForm, balance: parseFloat(e.target.value) || 0})}
                    style={{width: '100%', padding: '6px', marginTop: '4px'}}
                  />
                </label>
                <div>
                  <button type="submit" style={{ backgroundColor: '#007bff', color: 'white', padding: '6px 12px', border: 'none', borderRadius: '4px', marginRight: '10px', cursor: 'pointer' }}>
                    Save Changes
                  </button>
                  <button type="button" onClick={() => setIsEditing(false)} style={{ padding: '6px 12px', cursor: 'pointer' }}>
                    Cancel
                  </button>
                </div>
              </form>
            )}
          </div>
        ) : (
          <p style={{ color: '#17a2b8', fontStyle: 'italic' }}>
            Select an operational tracking asset element from the left matrix map to reveal running audit states or process adjustments.
          </p>
        )}
      </div>

    </div>
  );
}