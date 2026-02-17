import { useState, useEffect } from 'react';

export default function QuestForm() {
    const [monsters, setMonsters] = useState([]);
    const [formData, setFormData] = useState({
        name: '', difficulty: 1, reward: 1000, targetMonsterId: ''
    });

    useEffect(() => {
        fetch('http://localhost:8080/monsters').then(r => r.json()).then(setMonsters).catch(console.error);
    }, []);

    const handleSubmit = async (e) => {
        e.preventDefault();
        const token = localStorage.getItem('jwt_token');

        if (!token) {
            alert("⚠️ Debes ser ADMIN para publicar misiones.");
            return;
        }

        try {
            const res = await fetch('http://localhost:8080/quests', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}` // ✅ AQUÍ ESTÁ LA CLAVE
                },
                body: JSON.stringify({
                    ...formData,
                    difficulty: parseInt(formData.difficulty),
                    reward: parseFloat(formData.reward),
                    targetMonsterId: parseInt(formData.targetMonsterId)
                })
            });

            if(res.ok) {
                alert("✅ Misión publicada");
                window.location.reload();
            } else {
                alert(`❌ Error: ${res.status} (Posiblemente no eres ADMIN)`);
            }
        } catch (error) {
            alert("Error de conexión");
        }
    };

    const inputStyle = { backgroundColor: '#000000', border: 'none', padding: '10px', borderRadius: '4px', width: '100%', marginBottom: '10px' };
    const labelStyle = { color: '#000000', display: 'block', marginBottom: '5px', fontWeight: 'bold' };

    return (
        <article style={{ backgroundColor: '#2d3436', border: '2px solid #5d4037', borderRadius: '10px', padding: '20px', color: 'white' }}>
            <h3 style={{color: '#e67e22', borderBottom: '1px solid #636e72', paddingBottom: '10px'}}>📜 Nueva Misión</h3>
            <form onSubmit={handleSubmit}>
                <label style={labelStyle}>Título</label>
                <input name="name" onChange={e => setFormData({...formData, name: e.target.value})} style={inputStyle} required />
                <div className="grid">
                    <div><label style={labelStyle}>Recompensa</label><input type="number" onChange={e => setFormData({...formData, reward: e.target.value})} style={inputStyle} /></div>
                    <div><label style={labelStyle}>Dificultad</label><input type="number" min="1" max="10" onChange={e => setFormData({...formData, difficulty: e.target.value})} style={inputStyle} /></div>
                </div>
                <label style={labelStyle}>Objetivo</label>
                <select name="targetMonsterId" onChange={e => setFormData({...formData, targetMonsterId: e.target.value})} style={{...inputStyle, cursor: 'pointer'}} required>
                    <option value="">Selecciona monstruo...</option>
                    {monsters.map(m => <option key={m.id} value={m.id}>{m.name}</option>)}
                </select>
                <button type="submit" style={{backgroundColor: '#e67e22', border: 'none', width: '100%', padding: '10px', color: 'white', fontWeight: 'bold', cursor: 'pointer'}}>Publicar</button>
            </form>
        </article>
    );
}