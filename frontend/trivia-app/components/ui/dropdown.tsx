type DropdownItem = {
    label: string;
    value: string;
}

export default function Dropdown({
    label,
    items,
}: {
    label: string,
    items: DropdownItem[]
}) {
    return (
        <div className="flex flex-col gap-2">
            <label className="font-medium text-slate-500">{label}</label>
            <select className="rounded-xl border border-slate-300 px-4 py-3 shadow-sm">
            {items.map((item) => (
                <option className="text-black" key={item.value}>{item.label}</option>
            ))}
            </select>
        </div>
    );
}