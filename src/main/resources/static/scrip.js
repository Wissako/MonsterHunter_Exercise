const API_URL = 'http://localhost:8080';

// Al iniciar la página, cargamos TODO
document.addEventListener('DOMContentLoaded', () => {
    loadWeapons();   // Carga el Enum de Java
    loadMonsters();  // Carga los monstruos
    loadQuests();    // Carga las misiones
});

// --- 1. CARGAR ARMAS (PROGRAM DATA) ---
async function loadWeapons() {
    try {
        const response = await fetch(`${API_URL}/hunters/weapons`);
        if (response.ok) {
            const weapons = await response.json();
            const select = document.getElementById('hWeapon');

            weapons.forEach(weapon => {
                const option = document.createElement('option');
                option.value = weapon;
                option.textContent = weapon.replace(/_/g, ' '); // Quita guiones bajos para que quede bonito
                select.appendChild(option);
            });
        }
    } catch (error) {
        console.error("Error cargando armas:", error);
    }
}

// --- 2. CARGAR MONSTRUOS ---
async function loadMonsters() {
    const container = document.getElementById('monsters-container');
    container.innerHTML = '<p>Cargando...</p>';

    try {
        const response = await fetch(`${API_URL}/monsters`);
        const monsters = await response.json();

        container.innerHTML = ''; // Limpiar

        if(monsters.length === 0) {
            container.innerHTML = '<p>No hay monstruos registrados.</p>';
            return;
        }

        monsters.forEach(m => {
            const card = document.createElement('div');
            card.className = 'monster-card';
            // Usamos ( || 'Unknown') para evitar fallos si el dato es null
            card.innerHTML = `
                <h3>${m.name}</h3>
                <div class="stats">
                    <p><strong>Tipo:</strong> ${m.type || '???'}</p>
                    <p><strong>Amenaza:</strong> ⭐ ${m.threatLevel}</p>
                    <p><span class="tag element">⚡ ${m.element || 'None'}</span> 
                       <span class="tag weakness">💀 ${m.weakness || 'None'}</span></p>
                </div>
                <p class="small"><strong>Hábitats:</strong> ${(m.habitats || []).join(', ')}</p>
            `;
            container.appendChild(card);
        });
    } catch (error) {
        container.innerHTML = '<p class="error">Error de conexión con el servidor.</p>';
        console.error(error);
    }
}

// --- 3. CARGAR MISIONES (NUEVO) ---
async function loadQuests() {
    const container = document.getElementById('quests-container');

    try {
        const response = await fetch(`${API_URL}/quests`);
        const quests = await response.json();

        container.innerHTML = '';

        if(quests.length === 0) {
            container.innerHTML = '<p>No hay misiones disponibles.</p>';
            return;
        }

        quests.forEach(q => {
            const card = document.createElement('div');
            card.className = 'quest-card'; // Podrías añadir estilo en CSS para .quest-card
            card.innerHTML = `
                <h3>📜 ${q.name}</h3>
                <p><strong>Objetivo:</strong> ${q.targetMonsterName}</p>
                <p><strong>Dificultad:</strong> ${"⭐".repeat(q.difficulty)}</p>
                <p><strong>Recompensa:</strong> 💰 ${q.reward}z</p>
            `;
            container.appendChild(card);
        });
    } catch (error) {
        console.error("Error cargando misiones:", error);
    }
}

// --- 4. REGISTRAR CAZADOR ---
document.getElementById('createHunterForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const hunterData = {
        name: document.getElementById('hName').value,
        email: document.getElementById('hEmail').value,
        rank: parseInt(document.getElementById('hRank').value),
        mainWeapon: document.getElementById('hWeapon').value
    };

    try {
        const response = await fetch(`${API_URL}/hunters`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(hunterData)
        });

        if (response.ok) {
            alert('¡Bienvenido al Gremio, Cazador!');
            document.getElementById('createHunterForm').reset();
        } else {
            const error = await response.json();
            alert('Error: ' + (error.message || 'Datos inválidos'));
        }
    } catch (error) {
        alert('Error conectando con el servidor');
    }
});