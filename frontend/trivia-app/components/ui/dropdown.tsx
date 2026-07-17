type DropdownItem = {
    label: string;
    value: string;
}

export default function Dropdown({
    label,
    items,
    optionClicked
}: {
    label: string,
    items: DropdownItem[]
    optionClicked: (label: string, value: string) => void;
}) {
    return (
        
        <div className="flex flex-col gap-2">
            <label className="font-medium text-slate-500">{label}</label>
            <select 
                onChange={(e) => optionClicked?.(label, e.target.value)} 
                className="rounded-xl border border-slate-300 px-4 py-3 shadow-sm">
            {items.map((item) => (
                <option className="text-black" key={item.value} value={item.value}>{item.label}</option>
            ))}
            </select>
        </div>
    );
}